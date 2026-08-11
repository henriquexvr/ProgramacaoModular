# Atividade Complementar: Revisão para a Prova 3

**Pontifícia Universidade Católica de Minas Gerais**  
Bacharelado em Engenharia de Software – Campus Lourdes  
Programação Modular  
Prof. João Caram – Semestre 1/2026

---

Esta é uma atividade complementar da disciplina **Programação Modular**. Ela será aplicada tendo em vista a suspensão das aulas de 24/06. Seu objetivo é revisar suas habilidades em modelagem OO e criação de código modular utilizando Java, tendo sempre em vista os princípios SOLID, aspectos da modularidade e suas aplicações. A atividade serve, ainda, como revisão para a **Prova 3** da disciplina. Sua entrega **não é obrigatória**. O gabarito comentado estará disponível no Canvas em 26/06.

---

## Situação-Problema

Um novo software está sendo desenvolvido para equipar as centrais eletrônicas dos novos automóveis de uma montadora famosa. Esta marca produz veículos com propulsão pré-definida (motor à combustão ou motor elétrico) e modelos híbridos (têm os dois tipos de propulsão e podem escolher qual querem usar).

Os motores à combustão têm sua autonomia calculada pela quantidade de litros de combustível restantes no tanque multiplicada pelo consumo médio deste combustível (ex.: gasolina, 12km/l; etanol, 8km/l; flex, 10,5km/l). Os propulsores elétricos seguem uma regra parecida: a autonomia é relativa à carga da bateria em kWh e o consumo desta bateria (ex.: 6,5km/kWh). Existem baterias de diferentes capacidades e consumos, dependendo de suas tecnologias. O sistema de software precisará informar, para qualquer modelo de carro, qual é sua **autonomia no momento** e qual é sua **autonomia máxima**.

A montadora também quer que seja desenvolvido um aplicativo para o usuário final. Neste aplicativo, a pessoa pode cadastrar vários automóveis e, a partir do cadastro de um roteiro, o aplicativo deve responder qual seria o carro mais adequado para realizar aquele roteiro naquele momento. Os automóveis devem registrar os roteiros realizados por cada um e, recebendo como parâmetro um valor médio de combustível ou carga, conseguir responder quanto já gastaram, em reais, em sua utilização para deslocamentos.

---

## Questões

**1)** Utilizando todos os conceitos vistos até hoje na disciplina, **modele um diagrama de classes UML** para o problema proposto. O modelo deve incluir classes, relacionamentos, atributos e métodos necessários para resolver completamente o problema. Não é necessário incluir construtores e métodos get/set na resposta.

**2)** Seguindo seu modelo em (1), escreva código dos métodos:

- **a)** Método que calcula a autonomia máxima de um modelo híbrido.
- **b)** Método do aplicativo que indica ao usuário qual seria o melhor carro para determinado roteiro.
- **c)** Método do aplicativo que permite o cadastro de um novo carro por parte do usuário.
