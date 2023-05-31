package tqs.example.impostor.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.example.impostor.models.ACP;
import tqs.example.impostor.repository.ACPRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ACPServiceTest {
    @Mock
    private ACPRepository acpRepository;

    private ACPService acpService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        acpService = new ACPService(acpRepository);
    }

    @Test
    void createACP_ValidData_ReturnsTrue() {
        Long id = 1L;
        String address = "piwosz";
        float capacity = 10.0f;
        boolean result = acpService.createACP(id, address, capacity);
        assertTrue(result);
    }

    @Test
    void createACP_NullAddress_ReturnsFalse() {
        Long id = 1L;
        String address = null;
        float capacity = 10.0f;
        boolean result = acpService.createACP(id, address, capacity);
        assertFalse(result);
    }

    @Test
    void createACP_ZeroCapacity_ReturnsFalse() {
        Long id = 1L;
        String address = "piwosz";
        float capacity = 0.0f;
        boolean result = acpService.createACP(id, address, capacity);
        assertFalse(result);
    }

    @Test
    void createACP_AlreadyExists_ReturnsFalse() {
        Long id = 1L;
        String address = "piwosz";
        float capacity = 10.0f;
        when(acpRepository.existsById(id)).thenReturn(true);
        boolean result = acpService.createACP(id, address, capacity);
        assertFalse(result);
    }

    @Test
    void searchACPbyID_ExistingID_ReturnsOptionalACP() {
        Long id = 1L;
        ACP expectedACP = new ACP();
        when(acpRepository.findById(id)).thenReturn(Optional.of(expectedACP));
        Optional<ACP> result = acpService.getACPbyID(id);
        assertEquals(Optional.of(expectedACP), result);
    }

    @Test
    void searchACPbyID_NonExistingID_ReturnsEmptyOptional() {
        Long id = 1L;
        when(acpRepository.findById(id)).thenReturn(Optional.empty());
        Optional<ACP> result = acpService.getACPbyID(id);
        assertEquals(Optional.empty(), result);
    }
}
