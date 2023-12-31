package com.nthokar.spring2023.main.domain.chess.game;

import com.nthokar.spring2023.main.domain.chess.DTO.MoveDTO;
import com.nthokar.spring2023.main.domain.chess.logic.Move;
import com.nthokar.spring2023.main.domain.chess.logic.board.Board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import javax.swing.*;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {
    public final String id;

    public final Player whitePlayer;
    public final Player blackPlayer;

    @Getter
    State state = State.PREPARE;
    public enum State {
        PREPARE,
        IN_GAME,
        END
    }
    public Action onGameEnd;
    private Player whichTurn;
    private final Board board;
    private final Timer timer;

    public static class Builder {
        public final String id = UUID.randomUUID().toString();
        public Timer timer;
        public Player whitePlayer;
        public Player blackPlayer;

        public Board board;

        public Builder setWhitePlayer(Player whitePlayer){
            this.whitePlayer= whitePlayer;
            return this;
        }

        public Builder setBlackPLayer(Player blackPlayer){
            this.blackPlayer = blackPlayer;
            return this;
        }
        public Builder setBoard(Board board){
            this.board = board;
            return this;
        }
        public Builder setTimer(Timer timer){
            this.timer = timer;
            return this;
        }

        public Game build(){
            if (Objects.isNull(whitePlayer) || Objects.isNull(blackPlayer)){
                log.warn("can't build game without player(s)");
                throw new RuntimeException();
            }
            return new Game(this);
        }
    }
    protected Game(Game.Builder builder){
        this.board = builder.board;
        this.whitePlayer = builder.whitePlayer;
        this.blackPlayer = builder.blackPlayer;
        this.timer = builder.timer;
        this.id = builder.id;
    }
    public boolean applyMove(Move move, Player player) {
        if (state != State.IN_GAME) return false;
        log.info(whichTurn == whitePlayer ? "white's turn..." : "black's turn...");
        boolean isValid = board.moveFigureIfLegal(move)
                && whichTurn == player;
        if (isValid && board.getState() == Board.State.IN_GAME) {
            whichTurn = whichTurn == whitePlayer ? blackPlayer : whitePlayer;

            board.updateState();
            log.info("move applied");
            return true;
        }
        else {
            log.info("move isn't valid");
            return false;
        }
    }

    public boolean surrender(Player player) {
        return false;
    }
}
