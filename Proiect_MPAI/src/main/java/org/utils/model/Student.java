package org.utils.model;

import jakarta.persistence.*;
import org.designPatterns.AbstractFactory.TipBursa;
import org.designPatterns.State.State;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Studenti")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nume;
    private double media;
    private boolean participantCompetitii;
    private double venitFamilie;
    private int membriFamilie;
    private boolean zonaDefavorizata;
    private boolean problemeMedicale;
    private boolean decesInFamilie;
    private boolean situatieExceptionala;
    private int anulStudiu;
    private List<String> rejectionReasons = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bursa> burse = new ArrayList<>();

    public Student() {
    }

    public Student(Long id, String nume, double media, boolean participantCompetitii, double venitFamilie,
                   int membriFamilie, boolean zonaDefavorizata, boolean problemeMedicale, boolean decesInFamilie, boolean situatieExceptionala, int anulStudiu, List<String> rejectionReasons, List<Bursa> burse) {
        this.id = id;
        this.nume = nume;
        this.media = media;
        this.participantCompetitii = participantCompetitii;
        this.venitFamilie = venitFamilie;
        this.membriFamilie = membriFamilie;
        this.zonaDefavorizata = zonaDefavorizata;
        this.problemeMedicale = problemeMedicale;
        this.decesInFamilie = decesInFamilie;
        this.situatieExceptionala = situatieExceptionala;
        this.anulStudiu = anulStudiu;
        this.rejectionReasons = rejectionReasons;
        this.burse = burse;
    }

    public boolean isEligibleForPerformance(int rank, int totalStudents) {
        return rank < Math.ceil(totalStudents * 0.02);
    }

    public boolean isEligibleForMerit(int rank, int totalStudents) {
        int numPerformanta = (int) Math.ceil(totalStudents * 0.02);
        return rank >= numPerformanta && rank < numPerformanta + Math.ceil((totalStudents - numPerformanta) * 0.10);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public boolean isParticipantCompetitii() {
        return participantCompetitii;
    }

    public void setParticipantCompetitii(boolean participantCompetitii) {
        this.participantCompetitii = participantCompetitii;
    }

    public double getVenitFamilie() {
        return venitFamilie;
    }

    public void setVenitFamilie(double venitFamilie) {
        this.venitFamilie = venitFamilie;
    }

    public int getMembriFamilie() {
        return membriFamilie;
    }

    public void setMembriFamilie(int membriFamilie) {
        this.membriFamilie = membriFamilie;
    }

    public boolean isZonaDefavorizata() {
        return zonaDefavorizata;
    }

    public void setZonaDefavorizata(boolean zonaDefavorizata) {
        this.zonaDefavorizata = zonaDefavorizata;
    }

    public boolean isProblemeMedicale() {
        return problemeMedicale;
    }

    public void setProblemeMedicale(boolean problemeMedicale) {
        this.problemeMedicale = problemeMedicale;
    }

    public boolean isDecesInFamilie() {
        return decesInFamilie;
    }

    public void setDecesInFamilie(boolean decesInFamilie) {
        this.decesInFamilie = decesInFamilie;
    }

    public boolean isSituatieExceptionala() {
        return situatieExceptionala;
    }

    public void setSituatieExceptionala(boolean situatieExceptionala) {
        this.situatieExceptionala = situatieExceptionala;
    }

    public int getAnulStudiu() {
        return anulStudiu;
    }

    public void setAnulStudiu(int anulStudiu) {
        this.anulStudiu = anulStudiu;
    }

    public List<Bursa> getBurse() {
        return burse;
    }

    public String getTipuriBurse() {
        if (burse == null || burse.isEmpty()) {
            return State.RESPINSA.toString();
        }
        return burse.stream()
                .map(Bursa::getTip)
                .collect(Collectors.joining(", "));
    }

    public void setBurse(List<Bursa> burse) {
        this.burse = burse;
    }

    public List<String> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<String> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }
}

