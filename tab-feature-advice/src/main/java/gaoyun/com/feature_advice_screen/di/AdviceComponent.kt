package gaoyun.com.feature_advice_screen.di

import dagger.Component
import dagger.Module
import dagger.Provides
import gaoyun.com.core_utils.ComponentDependencies
import gaoyun.com.feature_advice_screen.AdviceFragment
import gaoyun.com.feature_advice_screen.AdviceViewModel
import gaoyun.com.feature_advice_screen.repository.AdviceRepository
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor

interface AdviceDependencies : ComponentDependencies {
    fun interactor(): AdviceRemoteRepositoryInteractor
}

@Component(dependencies = [AdviceDependencies::class], modules = [AdviceModule::class])
interface AdviceComponent {
    fun inject(fragment: AdviceFragment)
}

@Module
class AdviceModule {

    @Provides
    internal fun provideRepository(interactor: AdviceRemoteRepositoryInteractor): AdviceRepository {
        return AdviceRepository(interactor)
    }

    @Provides
    internal fun provideViewModel(repository: AdviceRepository): AdviceViewModel {
        return AdviceViewModel(repository)
    }

}