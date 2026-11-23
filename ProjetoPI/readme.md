Pong 

Implementação do jogo Pong desenvolvida em Java com o framework FXGL e gerenciada via Apache Maven.

Requisitos do Ambiente

Java Development Kit (JDK):17 ou superior
Apache Maven: 3.8 ou superior

Instruções de Execução

Execução via Terminal (Maven)

Acesse o diretório raiz do projeto (onde o arquivo `pom.xml` está localizado).
Compile o projeto e baixe as dependências:
   ```bash
   mvn clean compile
   ```
Inicie a aplicação:
   ```bash
   mvn exec:java -Dexec.mainClass="pong.PongJogo"
   ```

Execução via IDE (VS Code / IntelliJ)

1. Extraia o conteúdo do arquivo compactado.
2. Abra o diretório raiz contendo o `pom.xml` e a pasta `src` na IDE.
3. Aguarde a indexação das dependências do Maven.
4. Execute o método `main` da classe `src/main/java/pong/PongJogo.java`.

Modos de Jogo e Controles

O menu inicial disponibiliza três modos de partida:

-Clássico (PVP): Partida local entre dois jogadores. Vence quem alcançar 5 pontos primeiro.
-Jogador vs Bot: Partida contra o oponente controlado pelo sistema. Vence quem alcançar 5 pontos primeiro.
-Modo Arcade: Modo contínuo contra parede rebatedora, com contagem de tempo e incremento gradual na velocidade da bola a cada rebatida.

Mapeamento de Teclas

-Mover Raquete Esquerda ( `W` / `S` )
-Mover Raquete Direita ( `Seta para Cima` / `Seta para Baixo` )
-Reiniciar Partida (tela de fim de jogo) (`ESPAÇO` )
-Retornar ao Menu Principal ( `M` )

Observação: No Modo Arcade, a raquete pode ser controlada tanto pelas setas direcionais quanto pelas teclas `W` e `S`.

Estrutura do Projeto

├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── pong/
                ├── MenuPrincipal.java
                ├── PongJogo.java
                ├── PongSettings.java
                └── TipoEntidade.java
```