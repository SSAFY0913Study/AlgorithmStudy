#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <deque>
#define INTMAX 100000001
using namespace std;

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	int N;

	cin >> N;

	vector<int> dp(N + 1, INTMAX);
	dp[0] = 0;
	vector<int> cost(N + 1);

	for (int i = 1; i <= N; i++) {
		cin >> cost[i];	// ī���� ��� �Է�
	}

	for (int i = 1; i <= N; i++) {
		for (int j = i; j <= N; j++) {
			dp[j] = min(dp[j], dp[j - i] + cost[i]);	// i��° ī������ ���ſ� �����ϴ� �Ͱ� ������ ���� ����� �����ϴ� �� �� �ּҰ��� �����Ѵ�. 
		}
	}
	cout << dp[N];

	return 0;
}