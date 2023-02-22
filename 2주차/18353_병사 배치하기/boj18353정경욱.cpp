#include <iostream>
#include <algorithm>
#include <vector>
#include <limits.h>
using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int N, maxLen = 0;

	cin >> N;

	vector<int> arr(N + 1); //병사의 전투력을 기록할 배열
	vector<int> dp(N + 1, 1);	// 현재 병사가 맨 뒤라고 가정할 때 최대 길이를 저장할 배열

	for (int i = 1; i <= N; i++) {
		cin >> arr[i];	//병사의 전투력을 차례대로 입력받음

		for (int j = i - 1; j > 0; j--) {// 자신의 앞 병사를 살펴본다
			if (arr[j] > arr[i]) {// 자신의 앞 병사가 자기보다 크다면
				dp[i] = max(dp[i], dp[j] + 1); //현재의 dp[i]과 자기보다 큰 병사를 끝으로 한 최장길이 + 1 중에서 더 큰값을 채택
			}
		}
		maxLen = max(maxLen, dp[i]);	//결과 선택
	}

	cout << N - maxLen;	// 열외해야하는 병사 계산

	return 0;
}