# ProjetoCompiladores

## Integrantes 

Caio Arnoni - 22.221.019-7 

Guilherme Matias - 22.122.071-8
## Gramatica

ROGRAMA -> BLOCO

BLOCO -> COMANDO BLOCO | ε

COMANDO -> DECISAO | DECLARACAO | REPETICAO | ATRIBUICAO | FUNCAO | INCREMENTO | ESCREVA | EXECUTE

## Elementos basicos 
ID       -> [a-zA-Z][a-zA-Z0-9]*<br/>
NUM      -> [0-9]+ | [0-9]+"."[0-9]+<br/>
STRING   -> '"' (qualquer_caractere_exceto_queabra) '"'<br/>
BOOLEAN  -> "verdade" | "mentira"<br/>
TIPO     -> "texto" | "bool" | "int" | "dec"<br/>
VALOR    -> ID | NUM | BOOLEAN | STRING<br/>

## Expressões

EXPRESSAO -> EXPR_ARITMETICA | EXPR_LOGICA | VALOR

## Aritimetica

OPERACAO_MATEMATICA -> TERMO EXPR_MAT<br/>
EXPR_MAT            -> "+" TERMO EXPR_MAT | "-" TERMO EXPR_MAT | ε<br/>
TERMO               -> FATOR TERMO'<br/>
TERMO'              -> "*" FATOR TERMO' | "/" FATOR TERMO' | ε<br/>
FATOR               -> ID | NUM | "(" OPERACAO_MATEMATICA ")"<br/>

## Logica
EXPRESSAO_LOGICA    -> PRIM_LOG (EXPRESSAO_LOGICA')?<br/>
PRIM_LOG            -> ID | NUM | BOOLEAN | STRING | "(" EXPRESSAO_LOGICA ")"<br/>
EXPRESSAO_LOGICA'   -> OPERADOR_ARITMETICO EXPRESSAO_LOGICA EXPREND<br/>
                    | OPERADOR_RELACIONAL  EXPRESSAO_LOGICA EXPREND
                    | OPERADOR_LOGICO      EXPRESSAO_LOGICA EXPREND
EXPREND             -> EXPREND  (* zero ou mais repetições via recursão *) | ε<br/>

OPERADOR_ARITMETICO -> "+" | "-" | "*" | "/"<br/>
OPERADOR_RELACIONAL -> ">" | "<" | "==" | "<=" | ">=" | "!="<br/>
OPERADOR_LOGICO     -> "ou" | "e"<br/>

## Variaveis
(* DECLARACAO vira “craft” (forjar/criar item) *)
DECLARACAO -> TIPO ID "=" EXPRESSAO ";" 
            | "craft" TIPO ID "=" EXPRESSAO ";"   (* alias minecraft *)

(* ATRIBUICAO clássica OU via “hopper” (funil move valor) *)
ATRIBUICAO -> ID OPERADOR_ATRIB EXPRESSAO ";"
            | "hopper" ID "<-" EXPRESSAO ";"      (* minecraft *)

OPERADOR_ATRIB -> "=" | "+=" | "-="

(* INCREMENTO via “piston” (empurra valor) *)
INCREMENTO -> ID OPERADOR_INCREMENTO
            | "piston" ID ("++" | "--" | "+=" EXPRESSAO | "-=" EXPRESSAO)

OPERADOR_INCREMENTO -> "++" | "--"

(* -------- Estruturas de controle -------- *)
(* if/else com “comparator” (comparador) *)
DECISAO -> "comparator" EXPR_LOGICA_ENCAPSULADA BLOCO_ENCAPSULADO SE_AUX

SE_AUX  -> "comparator_else_if" CONDICAO_ENCAPSULADA BLOCO_ENCAPSULADO SE_AUX
         | "comparator_else" BLOCO_ENCAPSULADO
         | ε

EXPR_LOGICA_ENCAPSULADA -> "(" EXPRESSAO_LOGICA ")"
CONDICAO_ENCAPSULADA    -> "(" EXPRESSAO_LOGICA ")"
BLOCO_ENCAPSULADO       -> "{" BLOCO "}"

(* Laço com “repeater” (repetidor) *)
REPETICAO -> ENQUANTO | PARA

ENQUANTO -> "repeater" EXPR_LOGICA_ENCAPSULADA BLOCO_ENCAPSULADO
          | "repeater" "ticks" NUM BLOCO_ENCAPSULADO          (* repete N vezes *)

(* opcional: estilo for *)
PARA     -> "repeater" "steps" "("
             (DECLARACAO | ATRIBUICAO | INCREMENTO | ε) ";"
             (EXPRESSAO_LOGICA | ε) ";"
             (ATRIBUICAO | INCREMENTO | CHAMADA_FUNCAO | ε)
           ")"
           BLOCO_ENCAPSULADO

(* -------- Funções -------- *)
(* função como “command_block” (bloco de comando) *)
FUNCAO -> "command_block" ID "(" PARAMETROS ")" "{" BLOCO RETORNO "}"

PARAMETROS -> PARAMETRO | PARAMETRO "," PARAMETROS | ε
PARAMETRO  -> TIPO ID

CHAMADA_FUNCAO -> ID "(" ARGUMENTOS ")"
ARGUMENTOS     -> VALOR ARGUMENTO | ε
ARGUMENTO      -> "," ARGUMENTOS | ε

RETORNO -> "retorna" EXPRESSAO ";"

## Minecraft Grammar


| Regra                                          | FIRST                                                                                                                | FOLLOW                                                                                  |
| ---------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------- |
| **PROGRAMA**                                   | { **BLOCO** }                                                                                                        | { **\$** }                                                                              |
| **BLOCO**                                      | { **COMANDO**, ε }                                                                                                   | { `}`, **\$** }                                                                         |
| **COMANDO**                                    | { `comparator`, `repeater`, `hopper`, `piston`, `command_block`, `lamp`, `execute`, `craft`, **TIPO**, **ID**, `;` } | { inicia-COMANDO, `}`, **\$** }                                                         |
| **DECLARACAO**                                 | { `craft`, **TIPO** }                                                                                                | { inicia-COMANDO, `}`, **\$** }                                                         |
| **ATRIBUICAO**                                 | { **ID**, `hopper` }                                                                                                 | { inicia-COMANDO, `}`, **\$** }                                                         |
| **INCREMENTO**                                 | { **ID**, `piston` }                                                                                                 | { inicia-COMANDO, `}`, **\$** }                                                         |
| **DECISAO** (`comparator...`)                  | { `comparator` }                                                                                                     | { inicia-COMANDO, `}`, **\$** }                                                         |
| **REPETICAO** (`repeater...`)                  | { `repeater` }                                                                                                       | { inicia-COMANDO, `}`, **\$** }                                                         |
| **FUNCAO** (`command_block...`)                | { `command_block` }                                                                                                  | { inicia-COMANDO, `}`, **\$** }                                                         |
| **ESCREVA** (`lamp(...) ;`)                    | { `lamp` }                                                                                                           | { inicia-COMANDO, `}`, **\$** }                                                         |
| **EXECUTE** (`execute { … } enquanto ( … ) ;`) | { `execute` }                                                                                                        | { inicia-COMANDO, `}`, **\$** }                                                         |
| **TIPO**                                       | { `texto`, `bool`, `int`, `dec` }                                                                                    | { **ID**, `{` }                                                                         |
| **ID**                                         | { `[a–zA–Z]` }                                                                                                       | { `=`, `+=`, `-=`, `++`, `--`, `(`, `)`, `,`, `;`, `}`, `<-` }                          |
| **NUM**                                        | { `[0–9]` }                                                                                                          | { `+`, `-`, `*`, `/`, `>`, `<`, `==`, `<=`, `>=`, `!=`, `ou`, `e`, `)`, `;`, `,`, `]` } |
| **STRING**                                     | { `"`…`"` }                                                                                                          | { `+`, `==`, `!=`, `ou`, `e`, `)`, `;`, `,` }                                           |
| **BOOLEAN**                                    | { `verdade`, `mentira` }                                                                                             | { `==`, `!=`, `ou`, `e`, `)`, `;`, `,` }                                                |


(* -------- I/O e execução -------- *)
(* saída como “lamp” (acender/imprimir) *)
ESCREVA -> "lamp" "(" ARGUMENTOS ")" ";"

(* “execute … enquanto (…)” já é minecrafty, mantido *)
EXECUTE -> "execute" "{" BLOCO "}" "enquanto" "(" EXPRESSAO_LOGICA ")"
