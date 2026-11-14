
# PW Motors - Calculadora de Viabilidade Financeira Veicular

Aplicação móvel desenvolvida como **Projeto Integrador VI** do curso de **Engenharia da Computação** da **Universidade Virtual do Estado de São Paulo (UNIVESP)**, em parceria com a concessionária **PW Motors**.

O aplicativo auxilia consumidores e operadores comerciais na tomada de decisão financeira, comparando de forma automatizada e transparente os custos reais entre a **aquisição financiada** e a **locação por assinatura** de veículos.

---

## Links do Projeto

* **Testar o Aplicativo Online (Appetize):** [Acessar Simulador Interativo](https://appetize.io/app/b_yqneyuskrqponmuacxfcs73r3e)
* **Vídeo de Apresentação (YouTube):** [Assistir Demonstração do Projeto Integrador](https://www.youtube.com/watch?v=5yxocxC7u-M)

---

## Funcionalidades Principais

* **Comparativo Compra x Locação:** Calcula e confronta o custo líquido final do financiamento bancário versus a assinatura mensal no mesmo horizonte de tempo.
* **Matemática Financeira Aplicada (Tabela Price):** Processa parcelas fixas, juros compostos, valor de entrada, IPVA (4% a.a.), seguro, manutenção preventiva e depreciação acumulada do veículo.
* **Matriz de Taxas por Categoria:** Parametrização automática de juros e fatores de locação para diferentes tipos (**Moto, Carro, Caminhão**) e categorias (**Popular, Médio, Luxo**).
* **Máscara Monetária Dinâmica (BRL):** Formatação automática em tempo real no padrão brasileiro (`R$ 0,00`) via `CurrencyTextWatcher`.
* **Validação de Entradas e Compartilhamento:** Bloqueio de inconsistências (ex.: entrada maior ou igual ao valor do veículo) e geração de diagnóstico para compartilhamento rápido com o cliente.

---

## Matriz de Parâmetros Utilizados

| Tipo de Veículo | Categoria Popular | Categoria Médio | Categoria Luxo |
| :--- | :---: | :---: | :---: |
| **Moto** | 1,0% a.m. | 1,5% a.m. | 2,0% a.m. |
| **Carro** | 1,5% a.m. | 2,0% a.m. | 2,5% a.m. |
| **Caminhão** | 2,0% a.m. | 2,5% a.m. | 3,0% a.m. |

*(Base de locação diária parametrizada em 0,15% do valor do veículo com multiplicadores de 1,0x para Popular, 1,2x para Médio e 1,5x para Luxo).*

---

## Tecnologias Utilizadas

* **Versão Nativa Android (Este Repositório):**
  * **Linguagem:** Kotlin
  * **IDE:** Android Studio
  * **Arquitetura / UI:** XML Layouts, `ViewBinding`, `CurrencyTextWatcher`, SDK Mínimo API 24 (Android 7.0)
  * **Build System:** Gradle (Kotlin DSL)
* **Versão Multiplataforma (Protótipo Web/Mobile):**
  * React Native & Ecossistema Expo

---

## Como Executar o Projeto (Android Studio)

1. **Clone este repositório:**
   ```bash
   git clone https://github.com
   ```
2. **Abra no Android Studio:**
   * Vá em **File > Open** e selecione a pasta raiz do projeto (`CalculadoraVeiculos`).
3. **Configuração e Build:**
   * Aguarde a sincronização automática do **Gradle** (verifique se `viewBinding = true` está ativo no `build.gradle.kts`).
4. **Execução:**
   * Selecione um emulador Android (ex.: *Medium Phone API 36*) ou conecte um smartphone via USB.
   * Clique em **Run (`Shift + F10`)**.

---

## Autores e Orientação

**Alunos — Engenharia da Computação (UNIVESP - 2026):**
* Aparecido da Silva (RA 2110715)
* Erick Ramom Alvarenga Soares (RA 2216014)
* Jairo Oliveira Santos (RA 2208202)
* João Paulo Rodrigues Gregorio (RA 2209383)
* Lucas Politti de Azevedo (RA 2212288)
* Mariana Furlan (RA 2216673)
* Renan Saccenti Lopes (RA 2202469)
* Valéria Dias da Silva Romão (RA 2207971)

**Polos:** Americana/SP, Artur Nogueira/SP, Louveira/SP, Santa Bárbara d'Oeste/SP e Sumaré/SP.  
**Orientador / Tutor:** Prof. André Ricardo Souza e Silva.  
**Empresa Parceira:** PW Motors (Gestor: Sr. Kauê Galvão).

---
*Relatório Técnico-Científico e Software desenvolvidos para a disciplina de Projeto Integrador VI — UNIVESP (2026).*


## Como executar o projeto

1. **Clone este repositório:**
   ```bash
   git clone https://github.com
   ```
2. **Abra no Android Studio:**
   - Abra o Android Studio e selecione **File > Open**.
   - Navegue até a pasta onde você clonou o projeto e clique em **OK**.
3. **Sincronize e execute:**
   - Aguarde o Gradle baixar as dependências necessárias.
   - Conecte um dispositivo Android físico (com Depuração USB ativada) ou inicie um Emulador.
   - Clique no botão **Run** (ícone de *play* verde) na barra superior.

## Autor

Desenvolvido por **Studio Informática** (`studioinformatica`).

---
*Projeto desenvolvido para fins educacionais.*
