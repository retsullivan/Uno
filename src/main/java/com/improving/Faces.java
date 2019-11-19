package com.improving;

public enum Faces {

    Zero(0),One (1),Two(2),Three(3),Four(4),Five(5),
    Six(6), Seven(7), Eight(8), Nine(9),
    Reverse(20),Skip(20),Draw2(20),Draw4(50),Wild(50);

    int value;

    Faces (int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public Boolean equals(Faces face2){
        boolean isequal = false;

        if(this.ordinal()==face2.ordinal()){
            isequal=true;
        }

        return isequal;
    }




}