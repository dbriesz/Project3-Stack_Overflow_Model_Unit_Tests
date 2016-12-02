package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {

    private User user1;
    private User user2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        String topic = "Getting Started in Java";
        Board board = new Board(topic);
        user1 = new User(board, topic);
        user2 = new User(board, topic);
    }

    @Test
    public void upvotingQuestionReturnsCorrectIncreaseInRep() throws Exception {
        Question question = user1.askQuestion("Differences between HashMap and Hashtable?");
        Post post = question;
        user2.upVote(post);
        int reputation = user1.getReputation();

        assertEquals(5, reputation);
    }
}