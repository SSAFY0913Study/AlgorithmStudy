#include<iostream>
#include<string>
using namespace std;
string str, del, ans;
int main() {
	cin >> str >> del;
	int ds = del.size();
	for (char c : str) {
		ans += c;
		if (ans.size() >= ds&& ans.substr(ans.size() - ds, ds) == del)
			ans.erase(ans.end() - ds, ans.end());
	}
	if (ans == "")cout << "FRULA";
	else cout << ans;
	return 0;
}