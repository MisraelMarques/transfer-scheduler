<template>
  <main style="max-width: 1400px; margin: 24px auto; font-family: system-ui, -apple-system, Segoe UI, Roboto;">
    <!-- Header -->
    <header style="display:flex; justify-content:center; margin-bottom:20px;">
      <h1 style="font-size:22px; font-weight:700; text-align:center;">
        PAINEL DE TRANSFERÊNCIAS
      </h1>
    </header>

    <section style="display:grid; grid-template-columns: 1.2fr 2.2fr; gap:20px;">
      <!-- Dados + Agendar -->
      <div style="padding:30px; border:1px solid #e5e7eb; border-radius:12px;">
        <h2 style="font-size:18px; font-weight:700; margin:0 0 30px; text-align:center;">
          DADOS DE TRANSFERÊNCIA
        </h2>

        <ScheduleForm @scheduled="onScheduled" />
      </div>

      <!-- Extrato -->
      <div style="padding:30px; border:1px solid #e5e7eb; border-radius:12px;">
        <h2 style="font-size:18px; font-weight:700; margin:0 0 30px; text-align:center;">
          EXTRATO DE TRANSFERÊNCIAS
        </h2>
 
        <TransferList ref="transferListRef" />
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref } from 'vue'
import ScheduleForm from './components/ScheduleForm.vue'
import TransferList from './components/TransferList.vue'

const transferListRef = ref(null)

function onScheduled () {

  transferListRef.value?.reload()
}
</script>

<style scoped>
@media (max-width: 900px) {
  section {
    display: grid !important;
    grid-template-columns: 1fr !important;
  }
}
</style>
