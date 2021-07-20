package br.com.harbitech.school.alternative;

import br.com.harbitech.school.question.Question;

import javax.persistence.*;

@Entity
public class Alternative {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(columnDefinition = "TEXT")
   private String text;
   @Column(name = "order_visualization")
   private int orderVisualization;
   private boolean correct;
   private String justification;
   @ManyToOne
   private Question question;

   @Deprecated
   public Alternative() {}

   public Alternative(String text, Question question, boolean correct) {
      this.text = text;
      this.question = question;
      this.correct = correct;
   }

   @Override
   public String toString() {
      return "Alternative{" +
              "id=" + id +
              ", text='" + text + '\'' +
              ", orderVisualization=" + orderVisualization +
              ", correct=" + correct +
              ", justification='" + justification + '\'' +
              ", question=" + question +
              '}';
   }
}
