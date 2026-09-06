package dao;

import model.Mall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MallDAOTest {

    private final MallDAO dao = new MallDAO();

    @Test
    void saveAndReadBack() throws Exception {
        Mall mall = new Mall("Test Mall", 3000);
        dao.save(mall);

        assertTrue(mall.getId() > 0);

        Mall found = dao.findById(mall.getId());
        assertNotNull(found);
        assertEquals("Test Mall", found.getName());
        assertEquals(3000, found.getTotalArea());

        dao.delete(mall.getId());
    }
}