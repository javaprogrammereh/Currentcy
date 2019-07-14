package com.example.currencyconvertnew;

public class CalculatePrice {
    public Long calcRialToEuro(Long rial, int priceEuro) {
        int euro = priceEuro;
        Long Rial = rial;
        Long resultRialToEuro = Rial / euro;
        return resultRialToEuro;
    }

    public Long calcUsdToRial(Long dolar, int priceUsd) {
        int pric = priceUsd;
        Long usd = dolar;
        Long resultUsdToRial = usd * pric;
        return resultUsdToRial;
    }

    public Long calcUsdToEuro(Long dolar, int priceUsd, int priceEuro) {
        int pric = priceUsd;
        Long usd = dolar;
        Long valueUsdToRial = usd * pric;
        int pic2 = priceEuro;
        Long resultUsdToEuro = valueUsdToRial / pic2;
        return resultUsdToEuro;
    }

    public Long calcEuroToRial(Long euro, int priceEuro) {
        int pric = priceEuro;
        Long Euro = euro;
        Long resultEuroToRial = pric * Euro;
        return resultEuroToRial;
    }

    public Long calcEuroToUsd(Long euro, int priceUsd, int priceEuro) {
        int pric = priceEuro;
        Long Euro = euro;
        Long valueEuroToRial = pric * Euro;
        int pic2 = priceUsd;
        Long resultEuroToUsd = valueEuroToRial / pic2;
        return resultEuroToUsd;
    }

    public Long calcRialToUsd(Long rial, int priceUsd) {
        int dular = priceUsd;
        Long Rial = rial;
        Long resultRialToUSD = Rial / dular;
        return resultRialToUSD;
    }

}
