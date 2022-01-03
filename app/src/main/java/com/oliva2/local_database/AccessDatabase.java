package com.oliva2.local_database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.oliva2.models.BrandModel;
import com.oliva2.models.CategoryModel;
import com.oliva2.models.ProductModel;

import java.util.List;

public class AccessDatabase {
    private LocalDatabase localDatabase;
    private DAOInterface daoInterface;

    public AccessDatabase(Context context) {
        localDatabase = LocalDatabase.newInstance(context);
        daoInterface = localDatabase.daoInterface();
    }

public void clear(){
        localDatabase.clearAllTables();
}

    public void getCategory(DataBaseInterfaces.CategoryInterface categoryInterface) {
        new CategoryTask(categoryInterface).execute();
    }

    public void getBrand(DataBaseInterfaces.BrandInterface brandInterface) {
        new BrandTask(brandInterface).execute();
    }
    public void getProduct(DataBaseInterfaces.ProductInterface productInterface, String id, String name,int count,int offest) {
        new ProductTask(productInterface).execute(id,name,count+"",offest+"");
    }
    public void getallProduct(DataBaseInterfaces.AllProductInterface productInterface) {
        new AllProductTask(productInterface).execute();
    }
    public void getTax(DataBaseInterfaces.TaxInterface productInterface, int id) {
        new TaxTask(productInterface).execute(id+"");
    }
    public void getFirstStock(DataBaseInterfaces.FirstStockInterface productInterface, int id) {
        new FirstStockTask(productInterface).execute(id+"");
    }
    public void getUnit(DataBaseInterfaces.UnitInterface productInterface, int id) {
        new UnitTask(productInterface).execute(id+"");
    }
    public void getOffersProduct(DataBaseInterfaces.ProductOffersInterface productInterface, String id) {
        new ProductOffers(productInterface).execute(id);
    }
    public void insertCategory(CategoryModel retrieveModel, DataBaseInterfaces.CategoryInsertInterface retrieveInsertInterface) {
        new InsertCategoryTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertFirst(ProductModel.FirstStock retrieveModel, DataBaseInterfaces.FirstStockInsertInterface retrieveInsertInterface) {
        new InsertFisrtStockTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertTax(ProductModel.Tax retrieveModel, DataBaseInterfaces.TaxInsertInterface retrieveInsertInterface) {
        new InsertTaxTask(retrieveInsertInterface).execute(retrieveModel);
    }

    public void insertUnit(ProductModel.Unit retrieveModel, DataBaseInterfaces.UnitInsertInterface retrieveInsertInterface) {
        new InsertUnitTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertRetrieve(ProductModel retrieveModel, DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface) {
        new InsertRetrieveTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertOffer(List<ProductModel.OfferProducts> retrieveModel, DataBaseInterfaces.OfferInsertInterface retrieveInsertInterface) {
        new InsertOfferTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertBrand(BrandModel retrieveModel, DataBaseInterfaces.BrandInsertInterface retrieveInsertInterface) {
        new InsertBrandTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void udatefirststock(ProductModel.FirstStock retrieveModel, DataBaseInterfaces.FristStockupdateInterface retrieveInsertInterface) {
        new UpdateFirstStockTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void udateproduct(ProductModel retrieveModel, DataBaseInterfaces.ProductupdateInterface retrieveInsertInterface) {
        new UpdateProductTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public class InsertRetrieveTask extends AsyncTask<ProductModel, Void, Long> {
        private DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface;

        public InsertRetrieveTask(DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(ProductModel... lists) {
            boolean isInserted = false;
            long  data = daoInterface.insertRetrieveData(lists[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long bol) {

                retrieveInsertInterface.onRetrieveDataSuccess(bol);

        }
    }

    public class InsertCategoryTask extends AsyncTask<CategoryModel, Void, Long> {
        private DataBaseInterfaces.CategoryInsertInterface categoryInsertInterface;

        public InsertCategoryTask(DataBaseInterfaces.CategoryInsertInterface categoryInsertInterface) {
            this.categoryInsertInterface = categoryInsertInterface;
        }

        @Override
        protected Long doInBackground(CategoryModel... lists) {
            long data = daoInterface.insertCategoryData(lists[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long bol) {
           // Log.e("s;dldldldl",bol+"");

                categoryInsertInterface.onCategoryDataInsertedSuccess(bol);

        }
    }
    public class CategoryTask extends AsyncTask<Void, Void, List<CategoryModel>> {
        private DataBaseInterfaces.CategoryInterface categoryInterface;

        public CategoryTask(DataBaseInterfaces.CategoryInterface categoryInterface) {
            this.categoryInterface = categoryInterface;
        }

        @Override
        protected List<CategoryModel> doInBackground(Void... voids) {
            return daoInterface.getCategory();
        }

        @Override
        protected void onPostExecute(List<CategoryModel> categoryModelList) {
            categoryInterface.onCategoryDataSuccess(categoryModelList);
        }
    }
    public class ProductTask extends AsyncTask<String, Void, List<ProductModel>> {
        private DataBaseInterfaces.ProductInterface productInterface;

        public ProductTask(DataBaseInterfaces.ProductInterface productInterface) {
            this.productInterface = productInterface;
        }

        @Override
        protected List<ProductModel> doInBackground(String... strings) {

           if(strings[1].equals("featured")){
              // Log.e("lllll",strings[1]);
            return daoInterface.getProductByFeatured(Integer.parseInt(strings[2]),Integer.parseInt(strings[3]));}
            else  if(strings[1].equals("brand_id")){
                Log.e("d;d;;d",strings[2]+" "+strings[3]);
               return daoInterface.getProductByBrand(strings[0],Integer.parseInt(strings[2]),Integer.parseInt(strings[3]));
           }
            else {
               return daoInterface.getProductByCategory(strings[0],Integer.parseInt(strings[2]),Integer.parseInt(strings[3]));
           }
        }

        @Override
        protected void onPostExecute(List<ProductModel> productModelList) {

            productInterface.onProductDataSuccess(productModelList);
        }

    }
    public class AllProductTask extends AsyncTask<String, Void, List<ProductModel>> {
        private DataBaseInterfaces.AllProductInterface productInterface;

        public AllProductTask(DataBaseInterfaces.AllProductInterface productInterface) {
            this.productInterface = productInterface;
        }

        @Override
        protected List<ProductModel> doInBackground(String... strings) {


                return daoInterface.getallProduct();

        }

        @Override
        protected void onPostExecute(List<ProductModel> productModelList) {

            productInterface.onAllProductDataSuccess(productModelList);
        }

    }

    public class InsertFisrtStockTask extends AsyncTask<ProductModel.FirstStock, Void, Long> {
        private DataBaseInterfaces.FirstStockInsertInterface retrieveInsertInterface;

        public InsertFisrtStockTask(DataBaseInterfaces.FirstStockInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(ProductModel.FirstStock... retrieveModels) {
            long data = daoInterface.insertFirstStockData(retrieveModels[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long id) {

                retrieveInsertInterface.onFirstStockDataSuccess(id);

        }
    }
    public class InsertTaxTask extends AsyncTask<ProductModel.Tax, Void, Long> {
        private DataBaseInterfaces.TaxInsertInterface retrieveInsertInterface;

        public InsertTaxTask(DataBaseInterfaces.TaxInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(ProductModel.Tax ... retrieveModels) {
            long data = daoInterface.insertTaxData(retrieveModels[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long id) {

                retrieveInsertInterface.onTaxDataSuccess(id);

        }
    }
    public class InsertUnitTask extends AsyncTask<ProductModel.Unit, Void, Long> {
        private DataBaseInterfaces.UnitInsertInterface retrieveInsertInterface;

        public InsertUnitTask(DataBaseInterfaces.UnitInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(ProductModel.Unit... retrieveModels) {
            long data = daoInterface.insertUnitData(retrieveModels[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long id) {
                retrieveInsertInterface.onUnitDataSuccess(id);

        }
    }

    public class InsertOfferTask extends AsyncTask<List<ProductModel.OfferProducts>, Void, Boolean> {
        private DataBaseInterfaces.OfferInsertInterface retrieveInsertInterface;

        public InsertOfferTask(DataBaseInterfaces.OfferInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<ProductModel.OfferProducts>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertOfferData(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {

                retrieveInsertInterface.onOfferDataInsertedSuccess(bol);

        }
    }
    public class InsertBrandTask extends AsyncTask<BrandModel, Void, Long> {
        private DataBaseInterfaces.BrandInsertInterface retrieveInsertInterface;

        public InsertBrandTask(DataBaseInterfaces.BrandInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(BrandModel... lists) {
            boolean isInserted = false;
            long data = daoInterface.insertBrandData(lists[0]);

            return data;
        }

        @Override
        protected void onPostExecute(Long bol) {
                retrieveInsertInterface.onBrandDataInsertedSuccess(bol);

        }
    }
    public class BrandTask extends AsyncTask<Void, Void, List<BrandModel>> {
        private DataBaseInterfaces.BrandInterface brandInterface;

        public BrandTask(DataBaseInterfaces.BrandInterface brandInterface) {
            this.brandInterface = brandInterface;
        }

        @Override
        protected List<BrandModel> doInBackground(Void... voids) {
            return daoInterface.getBrand();
        }

        @Override
        protected void onPostExecute(List<BrandModel> brandModelList) {
            brandInterface.onBrandDataSuccess(brandModelList);
        }
    }
    public class TaxTask extends AsyncTask<String, Void, ProductModel.Tax> {
        private DataBaseInterfaces.TaxInterface productInterface;

        public TaxTask(DataBaseInterfaces.TaxInterface productInterface) {
            this.productInterface = productInterface;
        }

        @Override
        protected ProductModel.Tax doInBackground(String... strings) {

        return daoInterface.getProductTax(Integer.parseInt(strings[0]));
        }

        @Override
        protected void onPostExecute(ProductModel.Tax productModelList) {

            productInterface.onTaxDataSuccess(productModelList);
        }

    }
    public class FirstStockTask extends AsyncTask<String, Void, ProductModel.FirstStock> {
        private DataBaseInterfaces.FirstStockInterface productInterface;

        public FirstStockTask(DataBaseInterfaces.FirstStockInterface productInterface) {
            this.productInterface = productInterface;
        }

        @Override
        protected ProductModel.FirstStock doInBackground(String... strings) {

            return daoInterface.getProductFirstStock(Integer.parseInt(strings[0]));
        }

        @Override
        protected void onPostExecute(ProductModel.FirstStock productModelList) {

            productInterface.onFirstStockDataSuccess(productModelList);
        }

    }
    public class UnitTask extends AsyncTask<String, Void, ProductModel.Unit> {
        private DataBaseInterfaces.UnitInterface productInterface;

        public UnitTask(DataBaseInterfaces.UnitInterface productInterface) {
            this.productInterface = productInterface;
        }

        @Override
        protected ProductModel.Unit doInBackground(String... strings) {

            return daoInterface.getProductunit(Integer.parseInt(strings[0]));
        }

        @Override
        protected void onPostExecute(ProductModel.Unit productModelList) {

            productInterface.onUnitDataSuccess(productModelList);
        }

    }
    public class ProductOffers extends AsyncTask<String, Void, List<ProductModel.OfferProducts>> {
        private DataBaseInterfaces.ProductOffersInterface productInterface;

        public ProductOffers(DataBaseInterfaces.ProductOffersInterface productInterface) {
            this.productInterface = productInterface;
        }

        @Override
        protected List<ProductModel.OfferProducts> doInBackground(String... strings) {


                return daoInterface.getOffers(strings[0]);

        }

        @Override
        protected void onPostExecute(List<ProductModel.OfferProducts> productModelList) {

            productInterface.onProductOffersDataSuccess(productModelList);
        }

    }
    public class UpdateFirstStockTask extends AsyncTask<ProductModel.FirstStock, Void, Integer> {
        private DataBaseInterfaces.FristStockupdateInterface retrieveInsertInterface;

        public UpdateFirstStockTask(DataBaseInterfaces.FristStockupdateInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Integer doInBackground(ProductModel.FirstStock... retrieveModels) {
            int data = daoInterface.updateProductFirstStock(retrieveModels[0].getId(),retrieveModels[0].getQty());

            return data;
        }

        @Override
        protected void onPostExecute(Integer id) {

                retrieveInsertInterface.onFirstStockUpdateSuccess();

        }
    }
    public class UpdateProductTask extends AsyncTask<ProductModel, Void, Long> {
        private DataBaseInterfaces.ProductupdateInterface retrieveInsertInterface;

        public UpdateProductTask(DataBaseInterfaces.ProductupdateInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Long doInBackground(ProductModel... retrieveModels) {
            long data = daoInterface.updateProduct(retrieveModels[0].getId(),retrieveModels[0].getCount(),retrieveModels[0].getCan_make());

            return data;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id > 0) {
                retrieveInsertInterface.onproductUpdateSuccess();
            }
        }
    }


}
