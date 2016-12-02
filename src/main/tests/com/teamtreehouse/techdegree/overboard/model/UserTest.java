package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {

    private User user1;
    private User user2;
    private Question question1;
    private Answer answer1;
    private Post post1;
    private Post post2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        String topic = "Getting Started in Java";
        Board board = new Board(topic);
        user1 = new User(board, topic);
        user2 = new User(board, topic);

        question1 = new Question(user1, "Differences between HashMap and Hashtable?");
        post1 = user1.askQuestion(String.valueOf(question1));

        answer1 = new Answer(question1, user2, "Hashtable is synchronized, whereas HashMap is not...");
        post2 = user2.answerQuestion(question1, String.valueOf(answer1));
    }

    @Test
    public void questionUpvoteReturnsCorrectRepIncrease() throws Exception {
        user2.upVote(post1);
        int reputation1 = user1.getReputation();

        assertEquals(5, reputation1);
    }

    @Test
    public void answerUpvoteReturnsCorrectRepIncrease() throws Exception {
        user1.upVote(post2);
        int reputation2 = user2.getReputation();

        assertEquals(10, reputation2);
    }
}