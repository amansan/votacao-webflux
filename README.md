# Votação API

##Descrição
 Esta aplicação é uma API REST para gerenciar sessões de votação conforme especificação da Avaliação Técnica back-end V1, elaborada pelo Sicredi.
 Gerencia inclusão e exclusão de pautas de votação e associados votantes, assim como a abertura de sessões de votação para uma determinada pauta.
 
 
####Pauta
Uma pauta pode ser cadastrada através da API e possui um assunto, que deve ser informado ao cadastrar, e um total de votos 'Sim' e 'Nao', que serão contabilizados em uma sessão.

####Associado
Um associado possui nome e cpf, pelo qual será identificado para votar. 

####Sessão
Uma sessão de votação deve estar associada a uma pauta específica, que deve estar cadastrada e ser especificada para abertura da sessão.
Possui também uma duração, que pode ser informada em minutos na abertura da sessão. Se não informada, a sessão terá uma duração padrão de 1 minuto.
Cada voto é contabilizado na pauta que, ao fim da sessão, terá os totais de votos "Sim e "Nao".

####Voto
Um voto pode ter os valores "Sim e "Nao", e deve ser cadastrado em uma sessão ativa. Para que seja cadastrado, o voto deve ser feito por um associado em uma pauta cadastrada.
Um associado somente tem direito a um voto por pauta, e é identificado pelo CPF para tal.

##Especificações Técnicas
Esta aplicação foi desenvolvida usando Spring boot e WebFlux, com banco de dados MongoDB.
Ela roda em uma nuvem provida pela Heroku (https://www.heroku.com/home)
e o banco de dados roda no serviço Atlas (https://www.mongodb.com/cloud/atlas).
É utilizado JSON para a comunicação com a API.

####Código
O código está localizado na pasta `br.com.sicredi.desafio.votacao` e organizado nos seguintes pacotes: `controller`, `document`, `repository`, `request` e `service`.

Para implementar a sessão foi escolhido o conceito de SSE(Server-Sent-Events) por implementar o conceito de recebimento assíncrono de dados e emissão de eventos em um stream,
que neste caso é o recebimento dos votos na sessão.
Como os votos devem ser persistidos e o SSE exige que se tenha uma coleção "_Capped_", que é de tamanho limitado (Nesta aplicação tem o tamanho máximo de 50000), foi criado o conceito de Sessao/Voto,
que é esta coleção. A coleção é criada apenas para a sessão, sendo deletada antes do início da sessão seguinte.
Ela também é o documento "_Tailable_" no banco de dados, o que, junto com o fato de ser uma coleção "_Capped_", permite que seja feito o stream.
Como o stream não acontece se não há dados na coleção, ao iniciar a sessão é incluído um voto, que não é contabilizado, mas que permite que a stream comece para receber os votos válidos.


##Como utilizar a API
A aplicação roda numa nuvem da Heroku e possui o seguinte endpoint: `https://intense-tor-83678.herokuapp.com/`

Para criação, listagem e exclusão dos documentos devem ser usados, respectivamente, os métodos HTTP POST, GET e DELETE seguidos do path correspondente, conforme exemplos abaixo:

#####Pauta:

- Inclusão: POST `https://intense-tor-83678.herokuapp.com/pauta?assunto=<<assunto>>`
- Listagem: GET `https://intense-tor-83678.herokuapp.com/pauta`
- Exclusão: DELETE `https://intense-tor-83678.herokuapp.com/pauta`


#####Associado:

- Inclusão: POST `https://intense-tor-83678.herokuapp.com/associado`
    - Request body:
    `{"nome":"<<nome>>", "cpf":"<<CPF>>"}`

- Listagem: GET `https://intense-tor-83678.herokuapp.com/associado`
- Exclusão: DELETE `https://intense-tor-83678.herokuapp.com/associado`

#####Voto:

- Inclusão: POST `https://intense-tor-83678.herokuapp.com/votacao/voto`
    - Request body:
    `{"cpfAssociado":"<<CPF>>", "valor":"<<voto ('Sim' ou 'Não')>>", "pauta":"<<id da pauta>>"}`

- Listagem: GET `https://intense-tor-83678.herokuapp.com/associado`
- Exclusão: DELETE `https://intense-tor-83678.herokuapp.com/associado`

####Iniciar uma sessão de votação
- Com duração determinada (em minutos): GET `https://intense-tor-83678.herokuapp.com/votacao/sessao/nova?duracao=<<duracao>>&pauta=<<id da pauta>>`
- Sem duração determinada: GET `https://intense-tor-83678.herokuapp.com/votacao/sessao/nova?pauta=<<id da pauta>>`


##Contato
Em caso de qualquer problema com a execução da aplicação, entre em contato:
- Email: amansan@gmail.com
- Whatsapp: 51-985394403