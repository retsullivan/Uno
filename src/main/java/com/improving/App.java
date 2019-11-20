package com.improving;

import org.springframework.context.annotation.Configuration;
import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Uno!");
        System.out.println("How many players?");
        int numPlayers=scanner.nextInt();
        Game g = new Game(numPlayers);
        g.play(numPlayers);


        IGame aiGame = new AIGame(numPlayers);
        aiGame.play(numPlayers);

        Map<String, Integer> winCount = new HashMap<>();
        winCount.put("Neutral Player", 0);
        winCount.put("Smart Player", 0);
        numPlayers = 2;
        IPlayer winningPLayer = null;
        AIGame winCountGame = new AIGame();

        for (int i = 0; i < 1000; i++) {
                 winningPLayer = winCountGame.playGameReturnWinner(2);
            winCount.put(winningPLayer.getName(), winCount.get(winningPLayer.getName()) + 1);
        }
        for (var player : winCountGame.getPlayerInfo()) {
            System.out.println(player.getName() + " " + winCount.get(player.getName()));
        }


    }
}
