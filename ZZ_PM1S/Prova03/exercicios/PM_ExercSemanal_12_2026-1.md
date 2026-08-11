# Exercício Semanal 12

**Pontifícia Universidade Católica de Minas Gerais**  
Bacharelado em Engenharia de Software – Campus Lourdes  
Programação Modular  
Prof. João Caram – Semestre 1/2026  
**Data de entrega: 22/06/2026**

---

Esta é um exercício semanal da disciplina **Programação Modular**. O objetivo destes exercícios é reforçar o conteúdo estudado em aula nas semanas recentes da disciplina. O exercício deve ser feito **em papel** e entregue ao professor na data especificada. Sua entrega **não vale pontos diretamente**, mas é **obrigatória**. A entrega dos exercícios semanais será utilizada para calcular a nota proporcional dos exercícios de revisão pontuados.

---

## Enunciado

**12)** Uma empresa oferece cursos preparatórios para certificações para estudantes de TI. Quando a empresa foi aberta, comprou um sistema de informação para gerenciar os cursos, com os seguintes requisitos:

- Todo curso tem uma carga horária mínima de **30 horas/aula** e máxima de **120 horas/aula**.
- O valor do curso é o valor de sua hora, multiplicado pelo total de horas, mais o valor do material didático.
- O conteúdo de um material pode ser usado em vários cursos.
- Estudantes podem estar matriculados em diversos cursos.
- Um professor pode dar aula em muitos cursos. Para cada curso, o valor recebido é o valor das aulas do curso multiplicadas por 5.

O diagrama de classes original contempla as classes `Curso`, `MaterialDidatico`, `Aluno` e `Professor`, com seus respectivos atributos e métodos (conforme diagrama fornecido pelo professor).

Na expansão dos negócios, a empresa precisará de um sistema modernizado para atender aos novos requisitos:

- Passarão a existir **cursos online**. Como, nestes casos, o material não será impresso, não é cobrado. Por outro lado, estes cursos têm um conjunto de videoaulas. A carga horária do curso online será a soma das durações dos seus vídeos, acrescido de **20%**, que é o tempo de tutoria.
- Um curso online pode ser **avaliado por seus alunos**, com uma nota de 0 a 10. A avaliação do curso é a média das avaliações recebidas pelos alunos.
- Os **professores passarão a ser avaliados pelos alunos**. Esta avaliação é uma nota de 0 a 5. A avaliação de um professor é dada pelo valor obtido pela soma das notas positivas (4 ou 5), subtraída pela soma das notas negativas (1 ou 2); isso tudo dividido pela quantidade de notas positivas.

---

## Tarefa Obrigatória

Utilizando todos os conceitos estudados na disciplina, crie um **diagrama de classes UML** que represente uma solução completa para os requisitos apresentados. O modelo deve incluir classes, relacionamentos, atributos e métodos necessários para resolver o problema respeitando os princípios **SOLID**. Construtores não precisam ser incluídos na sua resposta. Os métodos getters e setters também não precisam ser incluídos.

---

## Tarefas Opcionais para Estudo

Implemente todas as classes envolvidas no problema e faça um pequeno programa de teste para o sistema. Neste programa devem ser cadastrados um curso de cada tipo e executadas operações para cada requisito descrito.
