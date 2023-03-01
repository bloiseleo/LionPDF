<img
     width="200px"
     src="./assets/logo_lionbot.svg">

# Introdução
O LionBOT é um bot que roda sobre a plataforma Discord pensado para facilitar a troca de informações entre diversos usuários que desejam compartilhar conhecimento. Com esse bot, você pode acessar PDFs e compartilhar PDFs com qualquer usuário que tenha contato com esse BOT em um servidor ou chat pessoal independente de servidor.

Através dele, um usuário que esteja em um servidor completamente aleatório pode fazer o upload de um documento PDF deixando-o, dessa forma, disponível para quem quiser fazer o download desse documento. O melhor de tudo é: o usuário que fez o Upload não precisa ter contato NENHUM com o usuário que fará a obtenção desses arquivos.

## Montando o Ambiente DEV

Esse ambiente é necessário para colaborar com o desenvolvimento e conseguir fazer os testes na sua própria máquina. Caso o seu intuíto seja ver funcionando, você irá precisar preencher as variáveis de ambiente com as suas informações e, também, o Docker.
Com esses dois passos completos corretamente, você irá conseguir rodar na sua máquina um ambiente com as exatas configurações de produção.

Para colaborar e desenvolver, você também pode seguir o mesmo fluxo acima. Contudo, recomenda-se o uso de uma IDE. Dentro desse repositório, há um `docker-compose` que é responsável por montar e orquestrar todo ambiente e simular o ambiente de produção o mais fielmente possível. Na data que esse README está sendo montado, não há outras tecnologias sendo utilizadas em ambiente de produção. Contudo, imagino que isso venha a mudar. Logo, qualquer serviço adicionado (como banco de dados), será adicionado ao `docker-compose` para manter a integridade do projeto.

### Instruções

- Clone o respositório na sua máquina.
- Crie um aplicativo através do site do [Discord](https://discord.com/developers/applications)
- Dentro do seu aplicativo, crie um bot clicando na opção Bot e, depois, Add Bot.
- Ative todos os intents, ative o PUBLIC BOT e desative OAuth2 CODE GRANT.
- Vá para aba OAuth2 e clique em General.
- Selecione dentro de scopes para BOT.
- Dentro de BOT Permissions clique em Administrator. A partir daqui, o seu BOT está configurado. Entretanto, para ele funcionar dentro do discord, você precisa convidá-lo.
- Para gerar o link de convite, clique em URL Generator dentro de OAuth2.
- Em seguida, selecione os mesmos escopos e permissões mencionados anteriormente.
- Copie o link e cole na sua URL.

Seguindo essas instruções, você consegu criar o BOT e colocá-lo dentro de algum servidor. Particularmente, recomendo a criação de um servidor pessoal somente para testes.
O seu bot está configurado e pronto para subir. Entretanto, antes disso, você precisa configurar a aplicação para ouvir os eventos dele. Para fazer isso, basta criar um arquivo .env seguindo o exemplo presente nesse repositório.
Depois disso, basta subir a sua aplicação utilizando docker-compose com esse seguinte comando:

```bash
docker-compose -f ./docker-compose.yml -p lionbot
```

