#include<iostream>
#include<string>
using namespace std;
string str, del, ans;
int main() {
	cin >> str >> del;
	int ds = del.size();
	for (char c : str) {
		ans += c;
		if (ans.size() >= ds && ans.substr(ans.size() - ds, ds) == del) //ans가 del.size보다 크고, 마지막 ds만큼의 길이가 del이랑 같으면 삭제
			ans.erase(ans.end() - ds, ans.end());
	}
	if (ans == "")cout << "FRULA";
	else cout << ans;
	return 0;
}