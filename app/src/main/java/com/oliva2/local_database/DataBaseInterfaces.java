package com.oliva2.local_database;



import com.oliva2.models.BrandModel;
import com.oliva2.models.CategoryModel;
import com.oliva2.models.ProductModel;

import java.util.List;

public class DataBaseInterfaces {


    public interface RetrieveInsertInterface {
        void onRetrieveDataSuccess(long bol);
    }
    public interface CategoryInsertInterface {
        void onCategoryDataInsertedSuccess(long bol);
    }
    public interface CategoryInterface {
        void onCategoryDataSuccess(List<CategoryModel> categoryModelList);
    }
    public interface ProductInterface {
        void onProductDataSuccess(List<ProductModel> productModelList);
    }
    public interface AllProductInterface {
        void onAllProductDataSuccess(List<ProductModel> productModelList);
    }
    public interface FirstStockInsertInterface {
        void onFirstStockDataSuccess(Long id);
    }
    public interface TaxInsertInterface {
        void onTaxDataSuccess(long id);
    }
    public interface UnitInsertInterface {
        void onUnitDataSuccess(long id);
    }
    public interface OfferInsertInterface {
        void onOfferDataInsertedSuccess(boolean bol);
    }
    public interface BrandInsertInterface {
        void onBrandDataInsertedSuccess(long bol);
    }
    public interface BrandInterface {
        void onBrandDataSuccess(List<BrandModel> brandModelList);
    }
    public interface TaxInterface {
        void onTaxDataSuccess(ProductModel.Tax productModelList);
    }
    public interface FirstStockInterface {
        void onFirstStockDataSuccess(ProductModel.FirstStock productModelList);
    }
    public interface UnitInterface {
        void onUnitDataSuccess(ProductModel.Unit productModelList);
    }
    public interface ProductOffersInterface {
        void onProductOffersDataSuccess(List<ProductModel.OfferProducts> productModelList);
    }
    public interface FristStockupdateInterface {
        void onFirstStockUpdateSuccess();
    }
    public interface ProductupdateInterface {
        void onproductUpdateSuccess();
    }
    public interface InsertInterface {
        void onInsertedFail();
    }
}
