package org.cfi.projectkhel;

import android.app.Application;

import org.cfi.projectkhel.data.DataManager;
import org.cfi.projectkhel.data.DataStorage;
import org.cfi.projectkhel.data.storage.LocalStorage;

/**
 * Maintains the application wide instances.
 */
public class KhelApplication extends Application {

  private MasterDataFetcher dataFetcher;

  public MasterDataFetcher getDataFetcher() {
    return dataFetcher;
  }

  public void setDataFetcher(MasterDataFetcher dataFetcher) {
    this.dataFetcher = dataFetcher;

//    DataStorage storage = new TestDataStorage();
    final DataStorage storage = new LocalStorage(dataFetcher.getStorageHandler());
    DataManager.getInstance().initialize(storage);
  }
}
