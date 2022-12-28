package it.unipi.lsmd.dto;

import it.unipi.lsmd.model.enums.Status;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class InvolvedPeopleDTO {
    private String organizer;
    private List<Pair<OtherUserDTO, Status>> joiners;

    public InvolvedPeopleDTO() {
        joiners = new ArrayList<>();
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public List<Pair<OtherUserDTO, Status>> getJoiners() {
        return joiners;
    }

    public void setJoiners(List<Pair<OtherUserDTO, Status>> joiners) {
        this.joiners = joiners;
    }

    public void addJoiners(Pair<OtherUserDTO, Status> j) {
        joiners.add(j);
    }
}
