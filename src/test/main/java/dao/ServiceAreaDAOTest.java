package dao;

import model.Floor;
import model.Mall;
import model.ServiceArea;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceAreaDAOTest {

    private final dao.MallDAO mallDAO = new dao.MallDAO();
    private final dao.FloorDAO floorDAO = new dao.FloorDAO();
    private final ServiceAreaDAO serviceAreaDAO = new ServiceAreaDAO();

    @Test
    void saveTwoServicesAndCheckSum() throws Exception {
        Mall mall = new Mall("Test Mall", 3000);
        mallDAO.save(mall);

        Floor floor = new Floor(mall.getId(), 1, 1000, 200, 50000);
        floorDAO.save(floor);

        serviceAreaDAO.save(new ServiceArea(floor.getId(), "Bathhroom", 20.0));
        serviceAreaDAO.save(new ServiceArea(floor.getId(), "Restaurant", 50.0));

        List<ServiceArea> areas = serviceAreaDAO.findByFloorId(floor.getId());

        double sum = 0;
        for (ServiceArea a : areas) {
            sum = sum + a.getSize();
        }

        assertEquals(2, areas.size());
        assertEquals(70.0, sum, 0.001);

        for (ServiceArea a : areas) {
            serviceAreaDAO.delete(a.getId());
        }
        floorDAO.delete(mall.getId());
        mallDAO.delete(mall.getId());


    }
}
