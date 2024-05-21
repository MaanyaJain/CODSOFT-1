import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessNumberGame extends JFrame {
    private int minRange;
    private int maxRange;
    private int randomNumber;
    private int attemptsLeft;
    private int score;

    private JLabel titleLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JTextArea feedbackArea;
    private JButton playAgainButton;

    public GuessNumberGame(int min, int max) {
        minRange = min;
        maxRange = max;
        attemptsLeft = 5; // You can adjust the number of attempts here
        score = 0;

        setTitle("Guess the Number Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        titleLabel = new JLabel("Guess a number between " + minRange + " and " + maxRange);
        add(titleLabel);

        guessField = new JTextField(10);
        add(guessField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        add(guessButton);

        feedbackArea = new JTextArea(10, 30);
        feedbackArea.setEditable(false);
        add(new JScrollPane(feedbackArea));

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(playAgainButton);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            if (guess < minRange || guess > maxRange) {
                feedbackArea.append("Please enter a number within the specified range.\n");
                return;
            }

            attemptsLeft--;

            if (guess == randomNumber) {
                feedbackArea.append("Congratulations! You guessed the correct number!\n");
                score += attemptsLeft + 1;
                playAgainButton.setVisible(true);
                guessButton.setEnabled(false);
            } else if (guess < randomNumber) {
                feedbackArea.append("Too low. ");
            } else {
                feedbackArea.append("Too high. ");
            }

            feedbackArea.append("Attempts left: " + attemptsLeft + "\n");

            if (attemptsLeft == 0) {
                feedbackArea.append("Sorry, you've run out of attempts. The correct number was " + randomNumber + ".\n");
                playAgainButton.setVisible(true);
                guessButton.setEnabled(false);
            }

            guessField.setText("");
            guessField.requestFocus();
        } catch (NumberFormatException ex) {
            feedbackArea.append("Please enter a valid number.\n");
        }
    }

    private void resetGame() {
        randomNumber = minRange + (int) (Math.random() * ((maxRange - minRange) + 1));
        attemptsLeft = 5;
        titleLabel.setText("Guess a number between " + minRange + " and " + maxRange);
        feedbackArea.setText("");
        playAgainButton.setVisible(false);
        guessButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GuessNumberGame game = new GuessNumberGame(1, 100);
                game.setVisible(true);
                game.resetGame();
            }
        });
    }
}
