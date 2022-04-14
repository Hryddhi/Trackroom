//
//  CreatorDetailedPostView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 27/3/22.
//

import SwiftUI
import Alamofire

struct CreateNewPostView: View {
    @State var postTitle: String = ""
    @State var postDescription: String = ""
    @State var postTypeSelection: String = "Text"
    @State var uploadImage: Bool = false
    @State var uploadDoc: Bool = false
    @State var uploadText: Bool = false
    @State var createNewQuiz: Bool = false
    @State var createPostSuccess: Bool = false
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    var postType: [String] = ["Text" , "Document", "Image", "Quiz"]
    
    var classPk: Int
    
    var body: some View {
        ZStack(alignment: .top){
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack{
                Text("Create A New Post")
                    .fontWeight(.bold)
                    .font(.title3)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 30,
                           idealHeight: 40,
                           maxHeight: 50,
                           alignment: .center)
                
                CustomTextField(textFieldLabel: "Post Title", textFieldInput: $postTitle, iconName: "character.bubble")
                CustomTextField(textFieldLabel: "Post Description", textFieldInput: $postDescription, iconName: "text.bubble")

                HStack {
                    Text("Post Type")
                        .fontWeight(.bold)
                    
                    Spacer()
                    
                    Picker(selection: $postTypeSelection,
                           content: {
                        ForEach(postType, id: \.self) {result in
                            Text(result)
                                .foregroundColor(Color.white)
                                .fontWeight(.bold)
                        }
                    }, label: {
                        HStack {
                            Text(postTypeSelection)
                        }
                    })
                        .frame(width: 75, height: 30)
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 32)
                        .background(Color("GreyColor"))
                        .cornerRadius(10)
                    
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 8)
                
                if postTypeSelection.contains("Text") {
                    Button {
                        textPost()
                    } label: {
                        Text("Submit")
                            .font(.body)
                            .fontWeight(.bold)
                            .foregroundColor(Color("PrimaryColor"))
                            .padding()
                    }
                }
                else if postTypeSelection.contains("Document") {
                    Text("Upload Document")
                        .font(.body)
                        .fontWeight(.bold)
                        .foregroundColor(Color("PrimaryColor"))
                        .padding()
                        .onTapGesture {
                            uploadDoc.toggle()
                        }
                        .fileImporter(isPresented: $uploadDoc, allowedContentTypes: [.pdf]) { result in
                            do {
                                let fileUrl = try result.get()
                                let contents = try Data(contentsOf: fileUrl)

                                print(fileUrl)
                                
                                documentPost(fileData: contents, fileName: "SampleFile")
                            }
                            catch {
                                print(error.localizedDescription)
                            }
                        }
                }
                else if postTypeSelection.contains("Image") {
                    Text("Upload Image")
                        .font(.body)
                        .fontWeight(.bold)
                        .foregroundColor(Color("PrimaryColor"))
                        .padding()
                        .onTapGesture {
                            uploadImage.toggle()
                        }
                        .fileImporter(isPresented: $uploadImage, allowedContentTypes: [.image]) { result in
                            do {
                                let fileUrl = try result.get()
                                print(fileUrl)
                            }
                            catch {
                                print(error.localizedDescription)
                            }
                        }
                }
                else if postTypeSelection.contains("Quiz") {
                    Text("Design Quiz")
                        .font(.body)
                        .fontWeight(.bold)
                        .foregroundColor(Color("PrimaryColor"))
                        .padding()
                        .sheet(isPresented: $createNewQuiz) {
                            QuizCreatorView()
                        }
                        .onTapGesture {
                            createNewQuiz.toggle()
                        }
                }
                else {
                    Button {
                        textPost()
                    } label: {
                        Text("Submit")
                            .font(.body)
                            .fontWeight(.bold)
                            .foregroundColor(Color("PrimaryColor"))
                            .padding()
                    }
                }
            }
        }
    }
    
    func textPost() {
        print("Inside Create Post Function")
        
        let access = UserDefaults.standard.string(forKey: "access")
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        
        let CREATE_NEW_POST = "http://20.212.216.183/api/classroom/\(classPk)/create-module/"
        
        let createNewPost = CreateNewPost(title: postTitle, description: postDescription, content_material: "")
        print(createNewPost)

        AF.request(CREATE_NEW_POST, method: .post, parameters: createNewPost, headers: header).responseJSON { response in
            let status = response.response?.statusCode
            print("Create Text Post Response : \(status)")
            if (status == 200) {
                self.presentationMode.wrappedValue.dismiss()
            }
        }
        
    }
    
    func documentPost(fileData: Data, fileName: String) {
        
        let access = UserDefaults.standard.string(forKey: "access")
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        
        let CREATE_NEW_POST = "http://20.212.216.183/api/classroom/\(classPk)/create-module/"
        
        AF.upload(multipartFormData: { multipart in
            
            //multipart.append(fileData, withName: "content_material")
            multipart.append(Data("SampleFile 2".utf8), withName: "title")
            multipart.append(Data("This is a sample ios file upload".utf8), withName: "description")
            multipart.append(fileData, withName: "content_material", fileName: "sample.pdf", mimeType: "application/pdf")
            
        }, to: CREATE_NEW_POST, method: .post, headers: header).responseJSON{ response in
            let status = response.response?.statusCode
            print("Create Document Post Response : \(status)")
            if (status == 200) {
                self.presentationMode.wrappedValue.dismiss()
            }
        }
    }
    
    func imagePost() {
        
    }
    
}

struct CreatorDetailedPostView_Previews: PreviewProvider {
    static var previews: some View {
        CreateNewPostView(classPk: 4)
    }
}
