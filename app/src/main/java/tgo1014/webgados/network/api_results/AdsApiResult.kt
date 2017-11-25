package tgo1014.webgados.network.api_results

import tgo1014.webgados.model.objects.Ad


data class AdsApiResult(var ads: List<Ad> = arrayListOf(), var adReverse: Boolean = false)