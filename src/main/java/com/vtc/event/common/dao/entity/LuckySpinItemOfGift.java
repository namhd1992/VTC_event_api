///***************************************************************************
// * Product made by Quang Dat *
// **************************************************************************/
//package com.vtc.event.common.dao.entity;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
///**
// * Author : Dat Le Quang
// * Email: Quangdat0993@gmail.com
// * Jan 4, 2020
// */
//@Entity
//@Table(name = "tblLuckySpinItemOfGift")
//@Setter
//@Getter
//@NoArgsConstructor
//public class LuckySpinItemOfGift {
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "luckySpinItem")
//    private LuckySpinItem luckySpinItem;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "luckySpinGift")
//    private LuckySpinGift luckySpinGift;
//
//}
