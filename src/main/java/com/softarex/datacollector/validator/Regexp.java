package com.softarex.datacollector.validator;

public class Regexp {
    public final static String PASSWORD_REGEXP = "(?=(.*[0-9]))((?=.*[A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z]))^.{8,40}$";
}
