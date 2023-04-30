// Generated by Dagger (https://dagger.dev).
package com.example.topnews.di;

import com.example.topnews.data.NewsLocalDataSource;
import com.example.topnews.data.db.ArticleDAO;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class LocalDataSourceModule_ProvidesNewsLocalDataSourceFactory implements Factory<NewsLocalDataSource> {
  private final LocalDataSourceModule module;

  private final Provider<ArticleDAO> articleDAOProvider;

  public LocalDataSourceModule_ProvidesNewsLocalDataSourceFactory(LocalDataSourceModule module,
      Provider<ArticleDAO> articleDAOProvider) {
    this.module = module;
    this.articleDAOProvider = articleDAOProvider;
  }

  @Override
  public NewsLocalDataSource get() {
    return providesNewsLocalDataSource(module, articleDAOProvider.get());
  }

  public static LocalDataSourceModule_ProvidesNewsLocalDataSourceFactory create(
      LocalDataSourceModule module, Provider<ArticleDAO> articleDAOProvider) {
    return new LocalDataSourceModule_ProvidesNewsLocalDataSourceFactory(module, articleDAOProvider);
  }

  public static NewsLocalDataSource providesNewsLocalDataSource(LocalDataSourceModule instance,
      ArticleDAO articleDAO) {
    return Preconditions.checkNotNullFromProvides(instance.providesNewsLocalDataSource(articleDAO));
  }
}