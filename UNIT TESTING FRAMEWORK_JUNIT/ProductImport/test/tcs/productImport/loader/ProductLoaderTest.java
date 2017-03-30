package test.tcs.productImport.loader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import tcs.productImport.dto.ProductDTO;
import tcs.productImport.loader.ProductLoader;
import atg.adapter.gsa.GSARepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryException;

@RunWith(MockitoJUnitRunner.class)
public class ProductLoaderTest {

	private static final String ID = "P1234";
	private static final String DISPLAY_NAME = "displayname";
	private static final String DESCRIPTION = "description";
	private static final String LONGDESCRIPTION = "longdescription";
	private static final String BRAND = "Nike";
	private String PRODUCT = "product";

	@Mock
	private GSARepository mockGsaRepository;

	@Mock
	private MutableRepositoryItem mockProductRepoItem;

	@InjectMocks
	private final ProductLoader testInstance = new ProductLoader();
	private ProductDTO productDTO;

	private void initiateProductDTO() {
		productDTO = new ProductDTO();
		productDTO.setId(ID);
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		initiateProductDTO();
		this.testInstance.setLoggingDebug(true);
		this.testInstance.setLoggingError(true);
		this.testInstance.setLoggingInfo(true);
		this.testInstance.setProductCatalog(mockGsaRepository);

	}

	@Test
	public void shouldloadAndCreateProduct() throws RepositoryException {
		productDTO.setBrand(BRAND);
		productDTO.setLongDescription(LONGDESCRIPTION);
		productDTO.setDisplayName(DISPLAY_NAME);
		productDTO.setDescription(DESCRIPTION);
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(null);
		when(mockGsaRepository.createItem(ID, "product")).thenReturn(
				mockProductRepoItem);
		this.testInstance.loadProduct(productDTO);
		verify(mockProductRepoItem, times(4)).setPropertyValue(anyString(),
				anyObject());
		verify(mockGsaRepository, times(1)).addItem(mockProductRepoItem);
	}

	@Test
	public void shouldloadAndUpdateProduct() throws RepositoryException {
		productDTO.setBrand(BRAND);
		productDTO.setLongDescription(LONGDESCRIPTION);
		productDTO.setDisplayName(DISPLAY_NAME);
		productDTO.setDescription(DESCRIPTION);
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(mockProductRepoItem);
		when(mockGsaRepository.getItemForUpdate(ID, "product")).thenReturn(
				mockProductRepoItem);
		this.testInstance.loadProduct(productDTO);
		verify(mockProductRepoItem, times(4)).setPropertyValue(anyString(),
				anyObject());
	}

	@Test
	public void shouldloadAndUpdateProductWithLogDebugFalse()
			throws RepositoryException {
		this.testInstance.setLoggingDebug(false);
		productDTO.setBrand(BRAND);
		productDTO.setLongDescription(LONGDESCRIPTION);
		productDTO.setDisplayName(DISPLAY_NAME);
		productDTO.setDescription(DESCRIPTION);
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(mockProductRepoItem);
		when(mockGsaRepository.getItemForUpdate(ID, "product")).thenReturn(
				mockProductRepoItem);
		this.testInstance.loadProduct(productDTO);
		verify(mockProductRepoItem, times(4)).setPropertyValue(anyString(),
				anyObject());
	}

	@Test
	public void shouldloadAndCreateProductItemWithoutProperties()
			throws RepositoryException {
		this.testInstance.setLoggingDebug(false);
		this.testInstance.setLoggingInfo(false);
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(null);
		when(mockGsaRepository.createItem(ID, "product")).thenReturn(
				mockProductRepoItem);
		this.testInstance.loadProduct(productDTO);
		verify(mockProductRepoItem, never()).setPropertyValue(anyString(),
				anyObject());
	}

	@Test(expected = RepositoryException.class)
	public void shouldNotloadAndCreateProductAndThrowException()
			throws RepositoryException {
		this.testInstance.setLoggingDebug(false);
		this.testInstance.setLoggingInfo(false);
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(null);
		when(mockGsaRepository.createItem(ID, "product")).thenThrow(
				RepositoryException.class);
		this.testInstance.loadProduct(productDTO);
		verify(mockProductRepoItem, never()).setPropertyValue(anyString(),
				anyObject());
	}

	@Test(expected = RepositoryException.class)
	public void shouldNotloadAndCreateProductAndThrowExceptionAndNotLog()
			throws RepositoryException {
		this.testInstance.setLoggingError(false);
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(null);
		when(mockGsaRepository.createItem(ID, "product")).thenThrow(
				RepositoryException.class);
		this.testInstance.loadProduct(productDTO);
		verify(mockProductRepoItem, never()).setPropertyValue(anyString(),
				anyObject());
	}

	@Test(expected = RepositoryException.class)
	public void shouldNotGetProductAndThrowException() throws Exception {
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT)).thenThrow(
				RepositoryException.class);
		this.testInstance.getProductItem(ID);
	}

	@Test
	public void shouldGetProduct() throws Exception {
		when(mockGsaRepository.getItem(productDTO.getId(), PRODUCT))
				.thenReturn(mockProductRepoItem);
		assertEquals(mockProductRepoItem, this.testInstance.getProductItem(ID));
	}

}
