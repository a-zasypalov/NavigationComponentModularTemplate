package gaoyun.com.feature_advice_screen.di

import com.example.persistence.repository.AdviceLocalRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import gaoyun.com.core_utils.ComponentDependencies
import gaoyun.com.feature_advice_screen.AdviceFragment
import gaoyun.com.feature_advice_screen.AdviceViewModel
import gaoyun.com.feature_advice_screen.repository.AdviceRepository
import gaoyun.com.network.domain.AdviceRemoteRepositoryInteractor
import java.time.chrono.ChronoLocalDate

interface AdviceDependencies : ComponentDependencies {
    fun interactor(): AdviceRemoteRepositoryInteractor
    fun localRepository(): AdviceLocalRepository
}

@Component(dependencies = [AdviceDependencies::class], modules = [AdviceModule::class])
interface AdviceComponent {
    fun inject(viewModel: AdviceViewModel)
}

@Module
class AdviceModule {

    @Provides
    internal fun provideRepository(interactor: AdviceRemoteRepositoryInteractor, localRepository: AdviceLocalRepository): AdviceRepository {
        return AdviceRepository(interactor, localRepository)
    }

}