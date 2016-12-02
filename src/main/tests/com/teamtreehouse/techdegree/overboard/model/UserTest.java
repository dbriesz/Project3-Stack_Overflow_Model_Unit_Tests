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
    private Question question;
    private Answer answer;
    private String votingMessage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        user1 = board.createUser("Alice");
        user2 = board.createUser("Bob");

        question = user1.askQuestion("Differences between HashMap and Hashtable?");
        answer = user2.answerQuestion(question, "Hashtable is synchronized, whereas HashMap is not...");

        votingMessage = "You cannot vote for yourself!";
    }

    @Test
    public void questionUpvoteReturnsCorrectRepIncrease() throws Exception {
        user2.upVote(question);

        assertEquals(5, user1.getReputation());
    }

    @Test
    public void answerUpvoteReturnsCorrectRepIncrease() throws Exception {
        user1.upVote(answer);

        assertEquals(10, user2.getReputation());
    }

    @Test
    public void answerAcceptanceReturnsCorrectRepIncrease() throws Exception {
        user1.acceptAnswer(answer);

        assertEquals(15, user2.getReputation());
    }

    @Test
    public void authorCannotUpvoteOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        user1.upVote(question);
    }

    @Test
    public void authorCannotUpvoteOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        user2.upVote(answer);
    }

    @Test
    public void authorCannotDownvoteOwnQuestion() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        user1.downVote(question);
    }

    @Test
    public void authorCannotDownvoteOwnAnswer() throws Exception {
        thrown.expect(VotingException.class);
        thrown.expectMessage("You cannot vote for yourself!");
        user2.downVote(answer);
    }

    @Test
    public void onlyQuestionerCanAcceptAnswer() throws Exception {
        User questioner = answer.getQuestion().getAuthor();
        String message = String.format("Only %s can accept this answer as it is their question",
                                       questioner.getName());
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage(message);
        user2.acceptAnswer(answer);
    }
}