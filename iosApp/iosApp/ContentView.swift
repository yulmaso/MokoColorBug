import Foundation
import SwiftUI
import Shared
import Combine
import WebKit
import UIKit

struct ContentView: View {
    @Environment(\.colorScheme) var colorScheme
    @State private var url = HtmlProvider().getHtmlString()
    
    var body: some View {
        VStack {
            HStack {
                Button("Light Theme") {
                    UIApplication.shared.setUserInterfaceStyle(.light)
                }
                Button("Dark Theme") {
                    UIApplication.shared.setUserInterfaceStyle(.dark)
                }
                Button("System Theme") {
                    UIApplication.shared.setUserInterfaceStyle(.unspecified)
                }
            }
            WebView(
                url: url,
                linkAction: { url = HtmlProvider().getHtmlString() }
            )
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .background(Color(SharedRes.colors().background.getUIColor()))
        .onChange(of: colorScheme) { _ in
            url = HtmlProvider().getHtmlString()
        }
    }
}

extension UIApplication {
    func setUserInterfaceStyle(_ style: UIUserInterfaceStyle) {
        connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap(\.windows)
            .forEach { window in
                window.overrideUserInterfaceStyle = style
            }
    }
}

struct WebView: UIViewRepresentable {
    var url: String?
    var linkAction: () -> ()
    
    func makeCoordinator() -> CustomCoordinator {
        Coordinator(self)
    }
    
    func makeUIView(context: Context) -> WKWebView {
        let webView = WKWebView()
        webView.navigationDelegate = context.coordinator
        return webView
    }
    
    func updateUIView(_ webView: WKWebView, context: Context) {
        if let urlValue = url {
            webView.loadHTMLString(urlValue, baseURL: nil)
        }
    }
    
}

class CustomCoordinator: NSObject, WKNavigationDelegate, WKUIDelegate {
    var parent: WebView
    
    init(_ parent: WebView) {
        self.parent = parent
    }
    
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {

        if navigationAction.navigationType == .linkActivated  {
            parent.linkAction()
            decisionHandler(.cancel)
        } else {
            decisionHandler(.allow)
        }
    }
}
