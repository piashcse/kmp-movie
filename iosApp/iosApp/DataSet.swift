//
//  DataSet.swift
//  iosApp
//
//  Created by Mehedi on 7/2/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct MovieItem{
    let id = UUID()
    let movieTitle:String
    let movieImage:String
}

struct MovieList{
    static let movies = [
        MovieItem(
            movieTitle: "Doctor Strange in the Multiverse of Madness", movieImage: "/61PVJ06oecwvcBisoAQu6SDfdcS.jpg"
        ),
        MovieItem(
            movieTitle: "Fantastic Beasts: The Secrets of Dumbledore", movieImage: "/jrgifaYeUtTnaH7NF5Drkgjg2MB.jpg"
        ),
        MovieItem(
            movieTitle: "Sonic the Hedgehog 2", movieImage: "/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg"
        )
    ]
}
