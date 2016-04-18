package ohtu;

public class TennisGame {
    
    private int player1Score, player2Score = 0;
    private String player1Name, player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name)) {
            player1Score++;
        } else {
            player2Score++;
        }
    }

    public String getScore() {
        if (victory()) {
            return "Win for " + leader();
        }
        if (advantage()) {
            return "Advantage " + leader();
        }
        if (deuce()) {
            return "Deuce";
        }
        if(scoreDifference() == 0) {
            return toTennis(player1Score) + "-All";
        }
        return toTennis(player1Score) + "-" + toTennis(player2Score);
    }

    private boolean victory() {
        return (player1Wins() || player2Wins());
    }

    private boolean player1Wins() {
        return (player1Score >= 4 && scoreDifference() >= 2);
    }

    private boolean player2Wins() {
        return (player2Score >= 4 && scoreDifference() <= -2 );
    }

    private boolean advantage() {
        return (player1HasAdvantage() || player2HasAdvantage());
    }

    private boolean player1HasAdvantage() {
        return (player1Score >= 4 && scoreDifference() >= 1);
    }

    private boolean player2HasAdvantage() {
        return (player2Score >= 4 && scoreDifference() <= -1);
    }

    private boolean deuce() {
        return scoreDifference() == 0 && player1Score >= 4;
    }

    // Positive : player1 leads
    // Zero     : even
    // Negative : player2 leads
    private int scoreDifference() {
        return player1Score - player2Score;
    }

    private String leader() {
        if (scoreDifference() > 0) {
            return player1Name;
        } else {
            return player2Name;
        }
    }

    private String toTennis(int score) {
        switch (score) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
        }
        return null;
    }
}