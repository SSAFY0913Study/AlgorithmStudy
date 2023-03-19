<<<<<<< HEAD
#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <stack>
#include <deque>
using namespace std;

string example, bomb, result = "";

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> example >> bomb;

	char bombLastChar = bomb[bomb.size() - 1];	// 폭발문자열의 마지막 문자

	int strSize = example.size();	// 입력문자열의 길이
	int bombSize = bomb.size();		// 폭발문자열의 길이

	for (int i = 0; i < strSize; i++) {
		result.push_back(example[i]);	//결과 문자열에 추가
		if (example[i] == bombLastChar) {	// 현재 검사하는 문자가 폭발문자열의 마지막 문자가 같다면 폭발할 수 있는지 검사한다.
			if (result.size() >= bombSize && result.substr(result.size() - bombSize, bombSize) == bomb) {	// 폭발할 수 있는 조건 1. 결과값의 문자열 길이가 폭발문자열의 길이보다 크거나 같아야한다. 조건 2. 결과값의 뒤에서부터 폭발문자열의 길이만큼 자른 것이 폭발문자열과 같다면 폭발한다.
				result.erase(result.size() - bombSize);	//문자열이 폭발하며 지워진다.
			}
		}
	}

	if (result.size() == 0) cout << "FRULA";
	else cout << result;

	return 0;
=======
#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <stack>
#include <deque>
using namespace std;

string example, bomb, result = "";

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> example >> bomb;

	char bombLastChar = bomb[bomb.size() - 1];	// 폭발문자열의 마지막 문자

	int strSize = example.size();	// 입력문자열의 길이
	int bombSize = bomb.size();		// 폭발문자열의 길이

	for (int i = 0; i < strSize; i++) {
		result.push_back(example[i]);	//결과 문자열에 추가
		if (example[i] == bombLastChar) {	// 현재 검사하는 문자가 폭발문자열의 마지막 문자가 같다면 폭발할 수 있는지 검사한다.
			if (result.size() >= bombSize && result.substr(result.size() - bombSize, bombSize) == bomb) {	// 폭발할 수 있는 조건 1. 결과값의 문자열 길이가 폭발문자열의 길이보다 크거나 같아야한다. 조건 2. 결과값의 뒤에서부터 폭발문자열의 길이만큼 자른 것이 폭발문자열과 같다면 폭발한다.
				result.erase(result.size() - bombSize);	//문자열이 폭발하며 지워진다.
			}
		}
	}

	if (result.size() == 0) cout << "FRULA";
	else cout << result;

	return 0;
>>>>>>> 3c536fed544afbeccb1b8ff7547975bf1dbaac01
}