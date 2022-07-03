import SwiftUI
import shared
import Kingfisher

struct ContentView: View {
	let greet = Greeting().greeting()
    let imageBaseUrl = "https://image.tmdb.org/t/p/w342"
    
    var movieData: [MovieItem] = MovieList.movies

    let columns = [
          GridItem(.adaptive(minimum: 150))
      ]
    var body: some View {
          ScrollView {
              LazyVGrid(columns: columns, spacing: 20) {
                  ForEach(movieData, id: \.id) { item in
                      HStack{
                          KFImage(URL(string: imageBaseUrl + item.movieImage))
                              .resizable()
                              .scaledToFit()
                              .frame(height:230)
                              .padding(.horizontal, 7)
                      }
                      
                  }
              }
              .padding(.horizontal)
          }
      }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
