package tcs.productImport.loader;

import tcs.productImport.dto.ProductDTO;
import tcs.util.Objects;
import atg.adapter.gsa.GSARepository;
import atg.nucleus.GenericService;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

public class ProductLoader extends GenericService {

	private GSARepository productCatalog;
	private String PRODUCT = "product";

	public void loadProduct(ProductDTO productDTO) throws RepositoryException {
		RepositoryItem productRepoItem = null;
		try {
			if (isLoggingInfo()) {
				logInfo("product loading started.");
			}
			productRepoItem = getProductItem(productDTO.getId());
			if (Objects.isEmpty(productRepoItem)) {
				createProduct(productDTO);
			} else {
				updateProduct(productDTO);
			}
		} catch (final RepositoryException e) {
			if (isLoggingError()) {
				logError("Error in creating Product: " + e.getId());
			}
			throw e;
		} finally {
			if (isLoggingInfo()) {
				logInfo("product loading completed.");
			}
		}
	}

	public RepositoryItem getProductItem(String productId)
			throws RepositoryException {
		RepositoryItem productRepoItem = null;

		productRepoItem = getProductCatalog().getItem(productId, PRODUCT);
		if (Objects.isNotEmpty(productRepoItem)) {
			return productRepoItem;
		} else {
			return null;
		}
	}

	public void createProduct(final ProductDTO productDTO)
			throws RepositoryException {
		if (isLoggingDebug()) {
			logDebug("Creating new Product: " + productDTO.getId());
		}
		final MutableRepositoryItem productRepoItem = getProductCatalog()
				.createItem(productDTO.getId(), PRODUCT);
		initProductRepositoryItem(productDTO, productRepoItem);
		getProductCatalog().addItem(productRepoItem);
		if (isLoggingDebug()) {
			logDebug("New Product created: " + productDTO.getId());
		}
	}

	public void updateProduct(final ProductDTO productDTO)
			throws RepositoryException {
		if (isLoggingDebug()) {
			logDebug("Updating existing Product: " + productDTO.getId());
		}
		final MutableRepositoryItem productRepoItem = getProductCatalog()
				.getItemForUpdate(productDTO.getId(), PRODUCT);
		initProductRepositoryItem(productDTO, productRepoItem);
		getProductCatalog().updateItem(productRepoItem);
		if (isLoggingDebug()) {
			logDebug("Updated existing Product: " + productDTO.getId());
		}
	}

	private void initProductRepositoryItem(final ProductDTO productDTO,
			final MutableRepositoryItem productRepoItem)
			throws RepositoryException {
		if (Objects.isNotEmpty(productDTO.getBrand())) {
			productRepoItem.setPropertyValue("brand", productDTO.getBrand());
		}
		if (Objects.isNotEmpty(productDTO.getDescription())) {
			productRepoItem.setPropertyValue("description",
					productDTO.getDescription());
		}
		if (Objects.isNotEmpty(productDTO.getLongDescription())) {
			productRepoItem.setPropertyValue("longDescription",
					productDTO.getLongDescription());
		}
		if (Objects.isNotEmpty(productDTO.getDisplayName())) {
			productRepoItem.setPropertyValue("displayName",
					productDTO.getDisplayName());
		}
	}

	public GSARepository getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(GSARepository productCatalog) {
		this.productCatalog = productCatalog;
	}

}
