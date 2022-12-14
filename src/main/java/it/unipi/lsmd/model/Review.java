package it.unipi.lsmd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Review {

    private String text;
    private String title;
    private Date date;
    private int value;
}
