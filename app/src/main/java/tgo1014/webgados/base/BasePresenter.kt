package tgo1014.webgados.base

interface BasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
}