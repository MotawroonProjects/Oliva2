package com.oliva2.local_database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


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



    public void getCategory(DataBaseInterfaces.CategoryInterface categoryInterface) {
        new CategoryTask(categoryInterface).execute();
    }

    public void getBrand(DataBaseInterfaces.BrandInterface brandInterface) {
        new BrandTask(brandInterface).execute();
    }
    public void getProduct(DataBaseInterfaces.ProductInterface productInterface, String id, String name) {
        new ProductTask(productInterface).execute(id,name);
    }

    public void insertCategory(List<CategoryModel> retrieveModel, DataBaseInterfaces.CategoryInsertInterface retrieveInsertInterface) {
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
    public void insertRetrieve(List<ProductModel> retrieveModel, DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface) {
        new InsertRetrieveTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertOffer(List<ProductModel.OfferProducts> retrieveModel, DataBaseInterfaces.OfferInsertInterface retrieveInsertInterface) {
        new InsertOfferTask(retrieveInsertInterface).execute(retrieveModel);
    }
    public void insertBrand(List<BrandModel> retrieveModel, DataBaseInterfaces.BrandInsertInterface retrieveInsertInterface) {
        new InsertBrandTask(retrieveInsertInterface).execute(retrieveModel);
    }

    public class InsertRetrieveTask extends AsyncTask<List<ProductModel>, Void, Boolean> {
        private DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface;

        public InsertRetrieveTask(DataBaseInterfaces.RetrieveInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<ProductModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertRetrieveData(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                retrieveInsertInterface.onRetrieveDataSuccess();
            }
        }
    }

    public class InsertCategoryTask extends AsyncTask<List<CategoryModel>, Void, Boolean> {
        private DataBaseInterfaces.CategoryInsertInterface categoryInsertInterface;

        public InsertCategoryTask(DataBaseInterfaces.CategoryInsertInterface categoryInsertInterface) {
            this.categoryInsertInterface = categoryInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<CategoryModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertCategoryData(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                categoryInsertInterface.onCategoryDataInsertedSuccess();
            }
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
            return daoInterface.getProductByFeatured(strings[0]);}
            else  if(strings[1].equals("brand_id")){
               return daoInterface.getProductByBrand(strings[0]);
           }
            else {
               return daoInterface.getProductByCategory(strings[0]);
           }
        }

        @Override
        protected void onPostExecute(List<ProductModel> productModelList) {
            productInterface.onProductDataSuccess(productModelList);
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
            if (id > 0) {
                retrieveInsertInterface.onFirstStockDataSuccess();
            }
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
            if (id > 0) {
                retrieveInsertInterface.onTaxDataSuccess();
            }
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
            if (id > 0) {
                retrieveInsertInterface.onUnitDataSuccess();
            }
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
            if (bol) {
                retrieveInsertInterface.onOfferDataInsertedSuccess();
            }
        }
    }
    public class InsertBrandTask extends AsyncTask<List<BrandModel>, Void, Boolean> {
        private DataBaseInterfaces.BrandInsertInterface retrieveInsertInterface;

        public InsertBrandTask(DataBaseInterfaces.BrandInsertInterface retrieveInsertInterface) {
            this.retrieveInsertInterface = retrieveInsertInterface;
        }

        @Override
        protected Boolean doInBackground(List<BrandModel>... lists) {
            boolean isInserted = false;
            long[] data = daoInterface.insertBrandData(lists[0]);
            if (data != null && data.length > 0) {
                isInserted = true;
            }
            return isInserted;
        }

        @Override
        protected void onPostExecute(Boolean bol) {
            if (bol) {
                retrieveInsertInterface.onBrandDataInsertedSuccess();
            }
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

}
