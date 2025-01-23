package org.utils.model;

import jakarta.persistence.*;
import org.designPatterns.AbstractFactory.TipBursa;
import org.designPatterns.State.State;

@Entity
@Table(name = "Burse")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip", discriminatorType = DiscriminatorType.STRING)
public abstract class Bursa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private TipBursa tip;

    private double sumaAlocata;

    @Enumerated(EnumType.STRING)
    private State state;

    public Bursa(double sumaBaza) {
        this.sumaAlocata = sumaBaza;
    }

    public String getTip() {
        return tip.toString();
    }

    public void setTip(TipBursa tip) {
        this.tip = tip;
    }

    public double getSumaAlocata() {
        return sumaAlocata;
    }

    public void setSumaAlocata(double sumaAlocata) {
        this.sumaAlocata = sumaAlocata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}


