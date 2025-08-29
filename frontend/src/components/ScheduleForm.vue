<template>
  <form @submit.prevent="submit" class="form">

    <!-- Nome + CPF -->
    <div class="row">
      <label class="field">NOME COMPLETO
        <input v-model="form.fullName" placeholder="Ex.: Maria Silva" class="input" />
      </label>

      <label class="field">CPF
        <input
          v-model="form.cpf"
          @input="onCpfInput"
          placeholder="000.000.000-00"
          maxlength="14"
          class="input"
        />
      </label>
    </div>

    <!-- Conta origem + Conta destino -->
    <div class="row">
      <label class="field">CONTA ORIGEM
        <input v-model="form.sourceAccount" placeholder="XXXXXXXXXX" maxlength="10" class="input" />
      </label>

      <label class="field">CONTA DESTINO
        <input v-model="form.targetAccount" placeholder="XXXXXXXXXX" maxlength="10" class="input" />
      </label>
    </div>

    <!-- Valor + Data de transferência -->
    <div class="row">
      <label class="field">VALOR
        <input v-model.number="form.amount" type="number" step="0.01" min="0.01" class="input" />
      </label>

      <label class="field">DATA DE TRANSFERÊNCIA
        <input v-model="form.transferDate" type="date" class="input" />
      </label>
    </div>

    <!-- Ações -->
    <div class="actions">
      <!-- Botão Simular  -->
      <button
        type="button"
        @click="quote"
        :disabled="!canQuote"
        class="btn btn-dark"
        :style="!canQuote ? 'opacity:0.6; cursor:not-allowed;' : ''"
      >
        SIMULAR TAXA
      </button>

      <!-- Botão Agendar -->
      <button
        v-if="mode !== 'quote-only'"
        type="submit"
        class="btn btn-primary"
      >
        AGENDAR
      </button>
    </div>

    <!-- Mensagens -->
    <p v-if="q.fee !== null" class="msg strong">
      TAXA: R$ {{ q.fee }} | TOTAL LÍQUIDO: R$ {{ q.total }}
    </p>
    <p v-if="error" class="msg error">{{ error }}</p>
    <p v-if="success" class="msg success">Agendado com sucesso.</p>
  </form>
</template>

<script setup>
import axios from 'axios'
import { reactive, computed, ref } from 'vue'

const props = defineProps({
  mode: { type: String, default: 'full' }
})
const emit = defineEmits(['scheduled'])

const form = reactive({
  fullName: '',
  cpf: '',
  sourceAccount: '',
  targetAccount: '',
  amount: null,
  transferDate: null
})

function onCpfInput(e) {
  let digits = e.target.value.replace(/\D/g, "").slice(0, 11)
  let formatted = digits

  if (digits.length > 9) {
    formatted = digits.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4")
  } else if (digits.length > 6) {
    formatted = digits.replace(/(\d{3})(\d{3})(\d{1,3})/, "$1.$2.$3")
  } else if (digits.length > 3) {
    formatted = digits.replace(/(\d{3})(\d{1,3})/, "$1.$2")
  }

  form.cpf = formatted
}

const q = reactive({ fee: null, total: null })
const error = ref('')
const success = ref(false)

const canQuote = computed(() => !!form.amount && !!form.transferDate)

async function quote () {
  error.value = ''
  q.fee = q.total = null
  try {
    const { data } = await axios.get('/api/transfers/quote', {
      params: { amount: form.amount, transferDate: form.transferDate }
    })
    q.fee = data.fee
    q.total = data.total
  } catch (e) {
    error.value = e?.response?.data?.message || e.message
  }
}

async function submit () {
  error.value = ''
  success.value = false
  try {
    await axios.post('/api/transfers', form)
    success.value = true
    q.fee = q.total = null
    form.fullName = ''
    form.cpf = ''
    form.sourceAccount = ''
    form.targetAccount = ''
    form.amount = null
    form.transferDate = null
    emit('scheduled')
  } catch (e) {
    error.value = e?.response?.data?.message || (e?.response?.data?.details?.join?.('; ') ?? e.message)
  }
}
</script>

<style scoped>
.form { display:flex; flex-direction:column; }
.row { display:grid; grid-template-columns:1fr 1fr; gap:24px; margin-bottom:16px; }
.field { font-weight:600; font-size:12px; display:flex; flex-direction:column; }
.input { width:100%; padding:10px 12px; border:1px solid #ddd; border-radius:8px; margin-top:6px; box-sizing:border-box; }
.actions { display:flex; justify-content:center; gap:20px; margin-top:6px; }
.btn { padding:12px 20px; font-size:14px; line-height:1.2; border-radius:8px; cursor:pointer; border:1px solid transparent; min-width:150px; text-align:center; }
.btn-dark { background:#111; color:#fff; border-color:#111; }
.btn-dark:hover { filter:brightness(1.05); }
.btn-primary { background:#2563eb; color:#fff; border-color:#2563eb; }
.btn-primary:hover { filter:brightness(1.06); }
.msg { text-align:center; margin:10px 0 0; }
.msg.strong{font-weight:600;} .msg.error{color:#b00;} .msg.success{color:#065f46;}
@media (max-width:900px){ .row{grid-template-columns:1fr; gap:12px;} .btn{min-width:0; width:100%;} .actions{flex-direction:column;} }
</style>
