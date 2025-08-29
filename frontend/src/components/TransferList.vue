<template>
  <div>
    <table class="transfer-table">
      <thead>
        <tr>
          <th v-for="h in headers" :key="h">{{ h }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="r in rows" :key="r.id">
          <td>{{ formatCpf(r.cpf) }}</td>
          <td>{{ r.fullName || '—' }}</td>
          <td>{{ r.sourceAccount }}</td>
          <td>{{ r.targetAccount }}</td>
          <td class="center">{{ formatMoney(r.amount) }}</td>
          <td class="center">{{ formatMoney(r.fee) }}</td>
          <td class="center">{{ formatMoney(r.total) }}</td>
          <td>{{ formatDateBR(r.transferDate) }}</td>
          <td>{{ formatDateTimeSP(r.scheduledAt) }}</td>
        </tr>
      </tbody>
    </table>
    <p v-if="!rows.length" class="empty">Nenhum agendamento ainda.</p>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onMounted } from 'vue'

const headers = [
  'CPF', 'NOME', 'ORIGEM', 'DESTINO', 'VALOR TRANSF.', 'TAXA',
  'VALOR LÍQUIDO', 'TRANSF.', 'AGENDADO'
]

const rows = ref([])

async function load () {
  const { data } = await axios.get('/api/transfers')
  rows.value = data
}
onMounted(load)
defineExpose({ reload: load })

// ----- Formatadores -----
function formatCpf(value) {
  if (!value) return '—'
  const digits = String(value).replace(/\D/g, '').padStart(11, '0').slice(-11)
  return digits.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')
}
const brl = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })
function formatMoney(n) {
  if (n === null || n === undefined) return '—'

  const val = typeof n === 'number' ? n : Number(n)
  return brl.format(val)
}
function formatDateBR(isoDate) {
 
  if (!isoDate) return '—'
  const [y,m,d] = isoDate.split('-').map(Number)
  return `${String(d).padStart(2,'0')}/${String(m).padStart(2,'0')}/${y}`
}
function formatDateTimeSP(iso) {
  if (!iso) return '—'
  
  const d = new Date(iso)
  return d.toLocaleString('pt-BR', {
    timeZone: 'America/Sao_Paulo',
    year: 'numeric', month: '2-digit', day: '2-digit',
    hour: '2-digit', minute: '2-digit'
  })
}
</script>

<style scoped>
.transfer-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  border-radius: 12px;
  overflow: hidden;
  border-bottom: 2px solid #e5e7eb;
  font-size: 13px;
}
th {
  text-align: left;
  border-bottom: 2px solid #e5e7eb;
  padding: 6px 8px;
  font-size: 12px;
  font-weight: 600;
}
td {
  padding: 8px;
  border-bottom: 1px solid #f1f5f9;
}
td.center { text-align: center; } 

.empty { margin-top: 8px; font-size: 13px; }
</style>
