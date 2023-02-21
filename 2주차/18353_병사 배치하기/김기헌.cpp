#include<iostream>
#include<algorithm>
using namespace std;
/*
LIS�� ���� n-LIS�� ���ϸ� �Ǵ� ����
���������� �����ϴ� �κм����� ���ϱ� ������, �迭�� ������
LIS�� �����ָ� �Ȱ���
*/
int main() {
	ios_base::sync_with_stdio(false), cin.tie(0), cout.tie(0);
	int n, arr[2005],lis[2005], idx = 1;
	cin >> n;
	for (int i = 0; i < n; i++)cin >> arr[i]; 
	if (n == 1) { //1�� �� �ݷ�
		cout << 0 << "\n";
		return 0;
	}
	reverse(arr, arr + n);
	lis[0] = arr[0]; //0���� �켱 �ְ�
	for (int i = 1; i < n; i++) { //LIS���ϴ� �˰���
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