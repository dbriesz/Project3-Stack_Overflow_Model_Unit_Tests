package com.teamtreehouse.techdegree.overboard.model;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        user1 = board.createUser("Alice");
        user2 = board.createUser("Bob");

        question = user1.askQuestion("Differences between HashMap and Hashtable?");
        answer = user2.answerQuestion(question, "Hashtable is synchronized, whereas HashMap is not...");
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
}