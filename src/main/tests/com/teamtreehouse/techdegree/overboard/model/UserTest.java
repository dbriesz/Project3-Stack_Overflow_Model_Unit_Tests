package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {

    private Board board;
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private Question question1;
    private Question question2;
    private Question question3;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;

    private String votingMessage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        user1 = board.createUser("Alice");
        user2 = board.createUser("Bob");
        user3 = board.createUser("Victor");
        user4 = board.createUser("Maria");

        question1 = user1.askQuestion("Differences between HashMap and Hashtable?");
        answer1 = user2.answerQuestion(question1, "Hashtable is synchronized, whereas HashMap is not...");

        question2 = user3.askQuestion("Looping through array in if statement to check if all values pass");
        answer2 = user4.answerQuestion(question2, "Use a loop...");

        question3 = user1.askQuestion("Tabs or spaces?");
        answer3 = user2.answerQuestion(question3, "Tabs, all day, every day!!!");

        votingMessage = "You cannot vote for yourself!";
    }

    @Test
    public void questionUpvoteReturnsCorrectRepIncrease() throws Exception {
        user2.upVote(question1);

        assertEquals(5, user1.getReputation());
    }

    @Test
    public void answerUpvoteReturnsCorrectRepIncrease() throws Exception {
        user1.upVote(answer1);

        assertEquals(10, user2.getReputation());
    }

    @Test
    public void answerAcceptanceReturnsCorrectRepIncrease() throws Exception {
        user1.acceptAnswer(answer1);

        assertEquals(15, user2.getReputation());
    }

    @Test
    public void authorCannotUpvoteOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage(votingMessage);
        user1.upVote(question1);
    }

    @Test
    public void authorCannotUpvoteOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage(votingMessage);

        user2.upVote(answer1);
    }

    @Test
    public void authorCannotDownvoteOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage(votingMessage);

        user1.downVote(question1);
    }

    @Test
    public void authorCannotDownvoteOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage(votingMessage);

        user2.downVote(answer1);
    }

    @Test
    public void onlyQuestionerCanAcceptAnswer() throws Exception {
        User questioner = answer1.getQuestion().getAuthor();
        String message = String.format("Only %s can accept this answer as it is their question",
                                       questioner.getName());

        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage(message);

        user2.acceptAnswer(answer1);
    }

    @Test
    public void downvotingCostsPoint() throws Exception {
        user1.upVote(answer1);
        user1.downVote(answer3);

        assertEquals(9, user2.getReputation());
    }
}