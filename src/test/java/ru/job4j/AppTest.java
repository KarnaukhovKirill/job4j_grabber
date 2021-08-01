package ru.job4j;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        int number = 10;
        App app = new App();
        var rsl = app.main(10);
        assertThat(rsl, is(100));
    }
}
