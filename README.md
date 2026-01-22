# Aplicativo sobre Pokemons
O presente repositório tem como objetivo apresentar um aplicativo sobre pokemons. 

## 🛠️ Configuração do Firebase (Importante)

Para rodar o projeto em sua máquina, siga os passos abaixo:

1. Acesse o [Console do Firebase](https://console.firebase.google.com/).
2. Crie um novo projeto (ou use um existente).
3. Adicione um aplicativo **Android** ao projeto.
   - **Importante:** O nome do pacote deve ser exatamente o mesmo que está no `build.gradle` deste projeto (`com.example.pokemon`).
4. Baixe o arquivo `google-services.json` gerado pelo Firebase.
5. Cole o arquivo na pasta `app/` dentro do diretório do projeto.
   - Caminho: `PokemonApp/app/google-services.json`
6. Ative o **Authentication** (Email/Senha) e o **Realtime Database** no console do Firebase.

Agora você pode compilar e rodar o aplicativo sem erros! 🚀
