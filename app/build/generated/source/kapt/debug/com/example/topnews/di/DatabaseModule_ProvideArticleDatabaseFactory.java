// Generated by Dagger (https://dagger.dev).
package com.example.topnews.di;

import android.app.Application;
import com.example.topnews.data.db.ArticleDatabase;
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
public final class DatabaseModule_ProvideArticleDatabaseFactory implements Factory<ArticleDatabase> {
  private final DatabaseModule module;

  private final Provider<Application> appProvider;

  public DatabaseModule_ProvideArticleDatabaseFactory(DatabaseModule module,
      Provider<Application> appProvider) {
    this.module = module;
    this.appProvider = appProvider;
  }

  @Override
  public ArticleDatabase get() {
    return provideArticleDatabase(module, appProvider.get());
  }

  public static DatabaseModule_ProvideArticleDatabaseFactory create(DatabaseModule module,
      Provider<Application> appProvider) {
    return new DatabaseModule_ProvideArticleDatabaseFactory(module, appProvider);
  }

  public static ArticleDatabase provideArticleDatabase(DatabaseModule instance, Application app) {
    return Preconditions.checkNotNullFromProvides(instance.provideArticleDatabase(app));
  }
}
