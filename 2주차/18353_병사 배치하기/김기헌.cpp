#include<iostream>
#include<algorithm>
using namespace std;
/*
LIS를 구해 n-LIS를 구하면 되는 문제
문제에서는 감소하는 부분수열을 원하기 때문에, 배열을 뒤집고
LIS를 구해주면 똑같다
*/
int main() {
	ios_base::sync_with_stdio(false), cin.tie(0), cout.tie(0);
	int n, arr[2005],lis[2005], idx = 1;
	cin >> n;
	for (int i = 0; i < n; i++)cin >> arr[i]; 
	if (n == 1) { //1일 때 반례
		cout << 0 << "\n";
		return 0;
	}
	reverse(arr, arr + n);
	lis[0] = arr[0]; //0번은 우선 넣고
	for (int i = 1; i < n; i++) { //LIS구하는 알고리즘
		int tmp = arr[i];
		if (lis[idx - 1] < tmp) {
			lis[idx++] = tmp;
			continue;
		}
		lis[lower_bound(lis, lis + idx, tmp) - lis] = tmp;
	}
	cout << n - idx << "\n";
	return 0;
}